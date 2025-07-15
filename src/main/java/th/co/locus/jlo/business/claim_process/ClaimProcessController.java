package th.co.locus.jlo.business.claim_process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.Multipart;
import th.co.locus.jlo.business.claim_process.bean.ClaimProcessCriteriaModelBean;
import th.co.locus.jlo.business.claim_process.bean.ClaimProcessModelBean;
import th.co.locus.jlo.business.claim_process.bean.DataExtractionModelBean;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

@RestController
@RequestMapping("api/claim-process")
public class ClaimProcessController extends BaseController {
	
	@Autowired
	private ClaimProcessService claimProcessService;

	@ReadPermission
	@PostMapping(value = "/searchDataExtraction", produces = "application/json")
	public ApiPageResponse<List<ClaimProcessModelBean>> searchDataExtraction(@RequestBody ApiPageRequest<ClaimProcessCriteriaModelBean> request) {
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ClaimProcessModelBean>> serviceResult = claimProcessService.searchDataExtraction(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@PostMapping(value = "/saveDataExtraction", produces = "application/json")
	public ApiResponse<ClaimProcessModelBean> saveDataExtraction(@RequestBody ApiRequest<DataExtractionModelBean> request) {
		ServiceResult<ClaimProcessModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {
			serviceResult = claimProcessService.updatePrompt(request.getData());
		} else {
			serviceResult = claimProcessService.createPrompt(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		
		if ("501".equals(serviceResult.getResponseCode())) {
			return ApiResponse.fail("DUPLICATE");
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@PostMapping(value = "/analyzeDataExtraction", produces = "application/json")
	public ApiResponse<ClaimProcessModelBean> analyzeDataExtraction(@RequestParam("file") MultipartFile file, @RequestParam("promptCode") String promptCode) {
		ServiceResult<ClaimProcessModelBean> serviceResult = claimProcessService.callGeminiAPI(promptCode, file);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		
		if ("501".equals(serviceResult.getResponseCode())) {
			return ApiResponse.fail("DUPLICATE");
		}
		return ApiResponse.fail();
	}

}
