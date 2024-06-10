/**
 * 
 */
package th.co.locus.jlo.business.loyalty.casesact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.casesact.bean.CaseActivityModelBean;
import th.co.locus.jlo.business.loyalty.casesact.bean.SearchCaseActivityModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

/**
 * @author Mr.BoonOom
 *
 */
@Api(value = "API for Case Activity Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/caseactivity")
public class CaseActivityController extends BaseController {

	@Autowired
	private CaseActivityService caseActivityService;

	 
	@ApiOperation(value = "Get Activity List Under Case ")
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

	@ApiOperation(value = "Create Activity Under Case")
	@PostMapping(value = "/createCaseActivity", produces = "application/json")
	public ApiResponse<CaseActivityModelBean> createCaseActivity(@RequestBody ApiRequest<CaseActivityModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		 
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

	@ApiOperation(value = "UpdateActivity Under Case")
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

	@ApiOperation(value = "Delete Activity by activity number")
	@PostMapping(value = "/deleteCaseActivity", produces = "application/json")
	public ApiResponse<Integer> deleteCaseActivity(@RequestBody ApiRequest<CaseActivityModelBean> request) {
		return ApiResponse.success(caseActivityService.deleteCaseActivity(request.getData()).getResult());
	}

	  

}
