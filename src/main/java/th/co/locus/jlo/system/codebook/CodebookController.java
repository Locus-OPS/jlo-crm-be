package th.co.locus.jlo.system.codebook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.codebook.bean.CodebookModelBean;
import th.co.locus.jlo.system.codebook.bean.SearchCodebookCriteriaModelBean;

@RestController
@RequestMapping("api/codebook")
public class CodebookController extends BaseController {
	
	@Autowired
	private CodebookService codebookService;
	
	@PostMapping(value = "/searchCodebook", produces = "application/json")
	public ApiPageResponse<List<CodebookModelBean>> searchCodebook(@RequestBody ApiPageRequest<SearchCodebookCriteriaModelBean> request) {
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CodebookModelBean>> serviceResult = codebookService.searchCodebook(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@PostMapping(value = "/saveCodebook", produces = "application/json")
	public ApiResponse<CodebookModelBean> saveCodebook(@RequestBody ApiRequest<CodebookModelBean> request) {
		ServiceResult<CodebookModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {
			serviceResult = codebookService.updateCodebook(request.getData());
		} else {
			serviceResult = codebookService.createCodebook(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

}
