package th.co.locus.jlo.system.business_unit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;
import th.co.locus.jlo.system.business_unit.bean.BusinessUnitModelBean;
import th.co.locus.jlo.system.business_unit.bean.SearchBusinessUnitModelBean;

@Slf4j
@Api(value = "API for Internationalization Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/business-unit")
public class BusinessUnitController extends BaseController {

	@Autowired
	private BusinessUnitService businessUnitService;

	@ReadPermission
	@ApiOperation(value = "Get Business Unit List")
	@PostMapping(value = "/getBusinessUnitList", produces = "application/json")
	public ApiPageResponse<List<BusinessUnitModelBean>> getBusinessUnitList(
			@RequestBody ApiPageRequest<SearchBusinessUnitModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<BusinessUnitModelBean>> serviceResult = businessUnitService
				.getBusinessUnitList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			Page<BusinessUnitModelBean> page = serviceResult.getResult();
			List<BusinessUnitModelBean> businessUnitList = page.getContent();
			if (businessUnitList != null) {
				log.debug("businessUnitList : " + businessUnitList);
			}
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create Business Unit")
	@PostMapping(value = "/createBusinessUnit", produces = "application/json")
	public ApiResponse<BusinessUnitModelBean> createBusinessUnit(
			@RequestBody ApiRequest<BusinessUnitModelBean> request) {
		request.getData().setCreatedBy(getUserId());
		ServiceResult<BusinessUnitModelBean> serviceResult;
		serviceResult = businessUnitService.createBusinessUnit(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Update Business Unit")
	@PostMapping(value = "/updateBusinessUnit", produces = "application/json")
	public ApiResponse<BusinessUnitModelBean> updateBusinessUnit(
			@RequestBody ApiRequest<BusinessUnitModelBean> request) {
		request.getData().setUpdatedBy(getUserId());
		ServiceResult<BusinessUnitModelBean> serviceResult;
		serviceResult = businessUnitService.updateBusinessUnit(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Delete Business Unit")
	@PostMapping(value = "/deleteBusinessUnit", produces = "application/json")
	public ApiResponse<Integer> deleteBusinessUnit(@RequestBody ApiRequest<BusinessUnitModelBean> request) {
		return ApiResponse.success(businessUnitService.deleteBusinessUnit(request.getData()).getResult());
	}
}
