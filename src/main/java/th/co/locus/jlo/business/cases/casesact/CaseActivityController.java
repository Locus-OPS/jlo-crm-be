/**
 * 
 */
package th.co.locus.jlo.business.cases.casesact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.cases.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.cases.casesact.bean.SearchCaseActivityModelBean;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

/**
 * @author Mr.BoonOom
 *
 */
@RestController
@RequestMapping("api/caseactivity")
public class CaseActivityController extends BaseController {

	@Autowired
	private CaseActivityService caseActivityService;

	 
	@PostMapping(value = "/getCaseActivityListByCaseNumber", produces = "application/json")
	public ApiPageResponse<List<CaseActivityModelBean>> getCaseActivityListByCaseNumber(
			@RequestBody ApiPageRequest<SearchCaseActivityModelBean> request) {
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CaseActivityModelBean>> serviceResult = caseActivityService.getCaseActivityListByCaseNumber(request.getData(),
				pageRequest);

		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@PostMapping(value = "/createCaseActivity", produces = "application/json")
	public ApiResponse<CaseActivityModelBean> createCaseActivity(@RequestBody ApiRequest<CaseActivityModelBean> request) {

		CommonUtil.nullifyObject(request.getData());
		 
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());

		ServiceResult<CaseActivityModelBean> serviceResult;

		serviceResult = caseActivityService.createCaseActivity(request.getData());

		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}

		return ApiResponse.fail();
	}

	@PostMapping(value = "/updateCaseActivity", produces = "application/json")
	public ApiResponse<CaseActivityModelBean> updateCaseActivity(@RequestBody ApiRequest<CaseActivityModelBean> request) {
		request.getData().setUpdatedBy(getUserId());
		
		ServiceResult<CaseActivityModelBean> serviceResult;
		serviceResult = caseActivityService.updateCaseActivity(request.getData());
		System.out.println(" updateCaseActivity : "+serviceResult.isSuccess());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@PostMapping(value = "/deleteCaseActivity", produces = "application/json")
	public ApiResponse<Integer> deleteCaseActivity(@RequestBody ApiRequest<CaseActivityModelBean> request) {
		return ApiResponse.success(caseActivityService.deleteCaseActivity(request.getData()).getResult());
	}

	  

}
