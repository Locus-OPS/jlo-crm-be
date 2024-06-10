package th.co.locus.jlo.business.loyalty.partner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.partner.bean.PartnerModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

@Api(value = "API for Loyalty Partner Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/loyalty")
public class PartnerController extends BaseController {
	
	@Autowired
	private PartnerService partnerService;
	
	@ApiOperation(value = "Get Partner List")
	@PostMapping(value = "/getPartnerList", produces = "application/json")
	public ApiPageResponse<List<PartnerModelBean>> getPartnerList(@RequestBody ApiPageRequest<PartnerModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PartnerModelBean>> serviceResult = partnerService.getPartnerList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Create or Update Partner")
	@PostMapping(value = "/savePartner", produces = "application/json")
	public ApiResponse<PartnerModelBean> savePartner(@RequestBody ApiRequest<PartnerModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<PartnerModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = partnerService.updatePartner(request.getData());
		} else {
			serviceResult = partnerService.createPartner(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	
	// From here -> Partner Type
	
	@ApiOperation(value = "Get Partner Type List")
	@PostMapping(value = "/getPartnerTypeList", produces = "application/json")
	public ApiPageResponse<List<PartnerModelBean>> getPartnerTypeList(@RequestBody ApiPageRequest<PartnerModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PartnerModelBean>> serviceResult = partnerService.getPartnerTypeList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Create or Update Partner Type")
	@PostMapping(value = "/savePartnerType", produces = "application/json")
	public ApiResponse<PartnerModelBean> savePartnerType(@RequestBody ApiRequest<PartnerModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<PartnerModelBean> serviceResult;
		
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = partnerService.updatePartnerType(request.getData());
		} else {
			serviceResult = partnerService.createPartnerType(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

}
