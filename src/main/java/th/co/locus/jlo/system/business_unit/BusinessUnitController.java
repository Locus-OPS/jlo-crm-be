package th.co.locus.jlo.system.business_unit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.business_unit.bean.BusinessUnitModelBean;
import th.co.locus.jlo.system.business_unit.bean.SearchBusinessUnitModelBean;

@Slf4j
@RestController
@RequestMapping("api/business-unit")
public class BusinessUnitController extends BaseController {

	@Autowired
	private BusinessUnitService businessUnitService;

	@ReadPermission
	@PostMapping(value = "/getBusinessUnitList", produces = "application/json")
	public ApiPageResponse<List<BusinessUnitModelBean>> getBusinessUnitList(
			@RequestBody ApiPageRequest<SearchBusinessUnitModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		
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
	@PostMapping(value = "/deleteBusinessUnit", produces = "application/json")
	public ApiResponse<Integer> deleteBusinessUnit(@RequestBody ApiRequest<BusinessUnitModelBean> request) {
		return ApiResponse.success(businessUnitService.deleteBusinessUnit(request.getData()).getResult());
	}
}
