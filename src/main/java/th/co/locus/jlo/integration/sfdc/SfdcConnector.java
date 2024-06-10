package th.co.locus.jlo.integration.sfdc;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.base.Function;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

import reactor.core.publisher.Mono;
import th.co.locus.jlo.integration.LineSfdcConfig;
import th.co.locus.jlo.integration.PrechatConfig;
import th.co.locus.jlo.integration.PrechatItem;
import th.co.locus.jlo.integration.sfdc.bean.ChatRequest;
import th.co.locus.jlo.integration.sfdc.bean.EntityFieldsMap;
import th.co.locus.jlo.integration.sfdc.bean.LiveAgentSession;
import th.co.locus.jlo.integration.sfdc.bean.Message;
import th.co.locus.jlo.integration.sfdc.bean.MessageResponse;
import th.co.locus.jlo.integration.sfdc.bean.PrechatDetail;
import th.co.locus.jlo.integration.sfdc.bean.PrechatEntity;

public class SfdcConnector {

	private boolean isEnd = false;
	private final SfdcSessionHandler sessionHandler;
	private final String lineId;
	private final LineMessagingClient lineMessagingClient;
	private final Map<String, String> paramMap;
	private final LineSfdcConfig lineSfdcConfig;
	private final PrechatConfig prechatConfig;
	private LiveAgentSession liveAgentSession;
		
	public SfdcConnector(SfdcSessionHandler sessionHandler, String lineId, Map<String, String> paramMap
			, LineMessagingClient lineMessagingClient, LineSfdcConfig lineSfdcConfig, PrechatConfig prechatConfig) {
		this.sessionHandler = sessionHandler;
		this.lineId = lineId;
		this.lineMessagingClient = lineMessagingClient;
		this.paramMap = paramMap;
		this.lineSfdcConfig = lineSfdcConfig;
		this.prechatConfig = prechatConfig;
	}
	
	public void init() {
		liveAgentSession = getSessionId();
		System.out.println(" --- get session done ---");

		// request chat
		sendChatRequest(liveAgentSession);
		System.out.println(" --- get send chat request done ---");

		// pulling messages
		getMessages(liveAgentSession);		
		
//		pushMessage("Please wait ...");
	}
	
	public LiveAgentSession getSessionId() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-LIVEAGENT-API-VERSION", lineSfdcConfig.getApiVersion());
		headers.add("X-LIVEAGENT-AFFINITY", "null");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<LiveAgentSession> result = restTemplate.exchange(lineSfdcConfig.getSessionUrl(), HttpMethod.GET, entity,
				LiveAgentSession.class);
		return result.getBody();
	}

	public boolean sendChatRequest(LiveAgentSession session) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-LIVEAGENT-API-VERSION", lineSfdcConfig.getApiVersion());
		headers.add("X-LIVEAGENT-AFFINITY", session.getAffinityToken());
		headers.add("X-LIVEAGENT-SESSION-KEY", session.getKey());
		headers.add("X-LIVEAGENT-SEQUENCE", "1");

		ChatRequest req = new ChatRequest();
		req.setOrganizationId(lineSfdcConfig.getOrgId());
		req.setDeploymentId(lineSfdcConfig.getDeploymentId());
		req.setButtonId(lineSfdcConfig.getButtonId());
		req.setSessionId(session.getId());
		req.setUserAgent("Lynx/2.8.8");
		req.setLanguage("en-US");
		req.setScreenResolution("1900x1080");
		req.setVisitorName("Line Chat");
		
		if (prechatConfig.getItems() != null && prechatConfig.getItems().size() > 0) {
			List<PrechatDetail> prechatDetails = new ArrayList<PrechatDetail>();
			List<PrechatEntity> prechatEntities = new ArrayList<PrechatEntity>();
			for (PrechatItem item : prechatConfig.getItems()) {
				PrechatDetail detail = new PrechatDetail();
				detail.setLabel(item.getLabel());
				detail.setValue(this.paramMap.get(item.getLabel()));
				detail.setDisplayToAgent(true);
				detail.setTranscriptFields(new String[] { item.getTranscriptField() });
				prechatDetails.add(detail);
			
				PrechatEntity entity = new PrechatEntity();
				entity.setEntityName(item.getEntityName());
				entity.setSaveToTranscript(item.getSaveToTranscript());
				EntityFieldsMap fields = new EntityFieldsMap();
				fields.setFieldName(item.getFieldName());
				fields.setLabel(item.getLabel());
				fields.setDoFind(true);
				fields.setExactMatch(true);
				fields.setDoCreate(false);
				entity.setEntityFieldsMaps(new EntityFieldsMap[] { fields });
				prechatEntities.add(entity);
			}
			req.setPrechatDetails(prechatDetails.toArray(new PrechatDetail[] {}));
			req.setPrechatEntities(prechatEntities.toArray(new PrechatEntity[] {}));
		}		
		
		req.setReceiveQueueUpdates(true);
		req.setPost(true);
		System.out.println(req.toString());
		HttpEntity<ChatRequest> httpEntity = new HttpEntity<>(req, headers);

		try {
			ResponseEntity<String> result = restTemplate.exchange(lineSfdcConfig.getRequestUrl(), HttpMethod.POST, httpEntity,
					String.class);
			return result.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void getMessages(LiveAgentSession session) {
		System.out.println(" >>>> Get Message <<<< ");
		Mono<MessageResponse> messages = WebClient.create(lineSfdcConfig.getMessagesUrl()).method(HttpMethod.GET)
			.header("X-LIVEAGENT-API-VERSION", lineSfdcConfig.getApiVersion())
			.header("X-LIVEAGENT-AFFINITY", session.getAffinityToken())
			.header("X-LIVEAGENT-SESSION-KEY", session.getKey())
			.retrieve()
			.onStatus(HttpStatus::isError, exceptionFunction)
			.bodyToMono(MessageResponse.class).timeout(Duration.ofMillis(15000));
		
		messages.subscribe(this::handleMessage, this::handleError);
	}
	
	private final Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction = response -> {
		System.out.println("Exception :: ");
		return response.bodyToMono(String.class)
		    .map(body -> {		    	
		    	return new RuntimeException(String.format("%s, response body: %s", response.statusCode().toString(), body));	
		    });
	};	
	
	public boolean sendChatEnd() {
		System.out.println(" -------------- send chat end to sfdc ");
		
		isEnd = true;
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("X-LIVEAGENT-API-VERSION", lineSfdcConfig.getApiVersion());
		headers.add("X-LIVEAGENT-AFFINITY", liveAgentSession.getAffinityToken());
		headers.add("X-LIVEAGENT-SESSION-KEY", liveAgentSession.getKey());
		
		HttpEntity<String> entity = new HttpEntity<>("{ \"reason\": \"" + this.lineSfdcConfig.getChatEndMessage() + "\" } ", headers);

		ResponseEntity<String> result = restTemplate.exchange(lineSfdcConfig.getChatEndUrl(), HttpMethod.POST, entity, String.class);
		return result.getStatusCode().is2xxSuccessful();
	}

	public boolean sendMessage(String text) {
		System.out.println(" -------------- send message to sfdc ");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.add("X-LIVEAGENT-API-VERSION", lineSfdcConfig.getApiVersion());
		headers.add("X-LIVEAGENT-AFFINITY", liveAgentSession.getAffinityToken());
		headers.add("X-LIVEAGENT-SESSION-KEY", liveAgentSession.getKey());
		
		HttpEntity<String> entity = new HttpEntity<>("{ \"text\": \"" + text + "\" } ", headers);

		ResponseEntity<String> result = restTemplate.exchange(lineSfdcConfig.getMessageUrl(), HttpMethod.POST, entity, String.class);
		return result.getStatusCode().is2xxSuccessful();
	}
	
	private void handleError(Throwable error) {
		if (TimeoutException.class.isInstance(error)) {
			if (!isEnd) {
				getMessages(liveAgentSession);
			}
		} else {
			System.out.println(" Exception : " + error.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleMessage(MessageResponse message) {
		System.out.println(message);
		for (Message msg : message.getMessages()) {
			if ("ChatMessage".equals(msg.getType())) {
				System.out.println(" >>>>>>>>>>>>>> on ChatMessage ");
				LinkedHashMap<String, String> chat = (LinkedHashMap<String, String>) msg.getMessage();
				pushMessage(chat.get("text"));
			}
			if ("ChatEnded".equals(msg.getType()) || "ChatRequestFail".equals(msg.getType())) {
				isEnd = true;
				if ("ChatEnded".equals(msg.getType())) {
					pushMessage(lineSfdcConfig.getSessionEndMsg());					
				} else {
					pushMessage(lineSfdcConfig.getUnavailableMessage());										
				}
				sessionHandler.destroy(this.lineId);
			}
		}
		if (!isEnd) {
			getMessages(liveAgentSession);			
		}
	}
	
	private void pushMessage(String text) {		
		PushMessage msg = new PushMessage(this.lineId, new TextMessage(text));
		try {
			this.lineMessagingClient.pushMessage(msg).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
