package th.co.locus.jlo.business.claim_process;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.aiplatform.v1.*;
import com.google.gson.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;

import th.co.locus.jlo.business.claim_process.bean.ClaimProcessCriteriaModelBean;
import th.co.locus.jlo.business.claim_process.bean.ClaimProcessModelBean;
import th.co.locus.jlo.business.claim_process.bean.DataExtractionModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.codebook.CodebookService;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;
import th.co.locus.jlo.system.codebook.bean.SearchCodebookCriteriaModelBean;

@Service
public class ClaimProcessServiceImpl extends BaseService implements ClaimProcessService {

    @org.springframework.beans.factory.annotation.Value("${vertex.project-id}")
    private String projectId;

    @org.springframework.beans.factory.annotation.Value("${vertex.location}")
    private String location;

    @org.springframework.beans.factory.annotation.Value("${vertex.key-path}")
    private String keyPath;

	@Autowired
	private CodebookService codebookService;

	@Override
	public ServiceResult<Page<ClaimProcessModelBean>> searchDataExtraction(ClaimProcessCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("claim-process.searchClaimProcess", criteria, pageRequest));
	}

	@Override
	public ServiceResult<ClaimProcessModelBean> createPrompt(DataExtractionModelBean bean) {
		try {
			int result = commonDao.update("claim-process.createDataExtraction", bean);
			if (result > 0) {
				return success(commonDao.selectOne("claim-process.findDataExtractionByAttId", bean));
			}
			return fail();
		} catch (DuplicateKeyException e) {
			return fail("501", e.getMessage());
		}
	}

	@Override
	public ServiceResult<ClaimProcessModelBean> updatePrompt(DataExtractionModelBean bean) {
		int result = commonDao.update("claim-process.updateDataExtraction", bean);
		if (result > 0) {
			return success(commonDao.selectOne("claim-process.findDataExtractionByAttId", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ClaimProcessModelBean> analyzeFile(MultipartFile file, String promptCode) {
		// Search prompt
		String prompt = "Extract data to xml in tag data";
		SearchCodebookCriteriaModelBean criteria = new SearchCodebookCriteriaModelBean();
		criteria.setCodeType("PROMPT_METHOD");
		ServiceResult<List<CodebookModelBean>> codebookSr = codebookService.searchCodebookNoPage(criteria);
		if (codebookSr.isSuccess()) {
			Optional<CodebookModelBean> result = codebookSr.getResult().stream()
					.filter(p -> p.getCodeId().equalsIgnoreCase(promptCode))
					.findFirst();
			if (result.isPresent()) {
				prompt = result.get().getDescription();
			} else {
				return fail();
			}
		} else {
			return fail();
		}

		PredictResponse response;
		try {
			GoogleCredentials credentials = GoogleCredentials
					.fromStream(getClass().getResourceAsStream(keyPath))
					.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

			PredictionServiceSettings settings = PredictionServiceSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
			try (PredictionServiceClient client = PredictionServiceClient.create(settings)) {
				// สร้าง Endpoint ของ Model
				String modelName = String.format(
						"projects/%s/locations/%s/publishers/google/models/%s",
						projectId, location, "gemini-2.0-flash-001" // หรือ "gemini-1.5-flash" ตามต้องการ
				);

				Map<String, Object> partText = Map.of("text", prompt);
				Map<String, Object> partFile = Map.of("inlineData", Map.of(
						"mimeType", file.getContentType(),
						"data", Base64.getEncoder().encodeToString(file.getBytes())
				));
				Map<String, Object> instance = Map.of("content", Map.of("parts", List.of(partText, partFile)));

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(instance);
				Value instanceValue = Value.newBuilder().setStructValue(
						Struct.newBuilder(mapper.readValue(json, Struct.class))
				).build();

				Struct parameters = Struct.newBuilder()
						.putFields("temperature", Value.newBuilder().setNumberValue(0.3).build())
						.putFields("maxOutputTokens", Value.newBuilder().setNumberValue(8192).build())
						.build();

				PredictRequest request = PredictRequest.newBuilder()
						.setEndpoint(modelName)
						.addInstances(instanceValue)
						.setParameters(Value.newBuilder().setStructValue(parameters).build())
						.build();
				response = client.predict(request);
			}

			if (response != null) {
				System.out.println(response.getPredictionsList().toString());
				ClaimProcessModelBean cp = new ClaimProcessModelBean();
				return success(cp);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fail();
	}

	@Override
	public ServiceResult<ClaimProcessModelBean> callGeminiAPI(String promptCode, MultipartFile imageFile) {
		// Search prompt
		String prompt = "Extract data to xml in tag data";
		SearchCodebookCriteriaModelBean criteria = new SearchCodebookCriteriaModelBean();
		criteria.setCodeType("PROMPT_METHOD");
		ServiceResult<List<CodebookModelBean>> codebookSr = codebookService.searchCodebookNoPage(criteria);
		if (codebookSr.isSuccess()) {
			Optional<CodebookModelBean> result = codebookSr.getResult().stream()
					.filter(p -> p.getCodeId().equalsIgnoreCase(promptCode))
					.findFirst();
			if (result.isPresent()) {
				prompt = result.get().getDescription();
			} else {
				return fail();
			}
		} else {
			return fail();
		}

		String endpoint = String.format(
				"https://us-central1-aiplatform.googleapis.com/v1/projects/%s/locations/%s/publishers/google/models/%s:generateContent",
				projectId, location, "gemini-2.0-flash-001" // หรือ "gemini-1.5-flash" ตามต้องการ
		);

		try {
			String accessToken = getAccessToken(); // ใช้ key.json เพื่อดึง token

			byte[] imageBytes = imageFile.getBytes();
			String mimeType = imageFile.getContentType();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);

			String requestBody = """
    {
      "contents": [
        {
          "role": "user",
          "parts": [
            { "text": "%s" },
            {
              "inlineData": {
                "mimeType": "%s",
                "data": "%s"
              }
            }
          ]
        }
      ],
      "generationConfig": {
        "temperature": 0.3,
        "maxOutputTokens": 8192,
        "topP": 0.95
      }
    }
    """.formatted(prompt, mimeType, base64Image);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(endpoint))
					.header("Authorization", "Bearer " + accessToken)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body().replaceAll("```json|```", "");
			JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
			JsonArray candidates = jsonObject.getAsJsonArray("candidates");
			JsonObject content = candidates.get(0).getAsJsonObject().getAsJsonObject("content");
			JsonArray parts = content.getAsJsonArray("parts");
			String partStr = parts.get(0).toString().replaceAll("\\\\n", "");
			JsonObject partObject = JsonParser.parseString(partStr).getAsJsonObject();
			JsonObject text = JsonParser.parseString(partObject.get("text").getAsString()).getAsJsonObject();
			JsonArray items = text.getAsJsonObject("medication_data").getAsJsonArray("item");


			for (JsonElement element : items) {
				JsonObject jObj = element.getAsJsonObject();

				// แปลง JsonObject เป็น Java Object
				Gson gson = new GsonBuilder()
						.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
						.create();
				ClaimProcessModelBean claimProcess = gson.fromJson(jObj, ClaimProcessModelBean.class);

				System.out.println("claimProcess: " + claimProcess);
			}

			if (response.body() != null || !"".equals(response.body())) {
				ClaimProcessModelBean cp = new ClaimProcessModelBean();
				return success(cp);
			}
			return fail();
		} catch (IOException e) {
			return fail("500", e.getMessage());
			// IOException, InterruptedException
		} catch (InterruptedException e) {
			return fail("500", e.getMessage());
			// IOException, InterruptedException
		}
	}

	private String getAccessToken() throws IOException {
		GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream(keyPath))
				.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
		credentials.refreshIfExpired();
		return credentials.getAccessToken().getTokenValue();
	}

}
