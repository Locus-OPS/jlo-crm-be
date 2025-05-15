package th.co.locus.jlo.business.prompt_manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.prompt_manage.bean.PromptManageCriteriaModelBean;
import th.co.locus.jlo.business.prompt_manage.bean.PromptManageModelBean;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

@RestController
@RequestMapping("api/prompt-management")
public class PromptManageController extends BaseController {
	
	@Autowired
	private PromptManageService promptManageService;
	
	@PostMapping(value = "/searchPrompt", produces = "application/json")
	public ApiPageResponse<List<PromptManageModelBean>> searchPrompt(@RequestBody ApiPageRequest<PromptManageCriteriaModelBean> request) {
		PageRequest pageRequest = getPageRequest(request);
		request.getData().setCodeType("PROMPT_METHOD");
		ServiceResult<Page<PromptManageModelBean>> serviceResult = promptManageService.searchPrompt(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@PostMapping(value = "/savePrompt", produces = "application/json")
	public ApiResponse<PromptManageModelBean> savePrompt(@RequestBody ApiRequest<PromptManageModelBean> request) {
		ServiceResult<PromptManageModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCodeType("PROMPT_METHOD");
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {
			serviceResult = promptManageService.updatePrompt(request.getData());
		} else {
			serviceResult = promptManageService.createPrompt(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		
		if ("501".equals(serviceResult.getResponseCode())) {
			return ApiResponse.fail("DUPLICATE");
		}
		return ApiResponse.fail();
	}

}
