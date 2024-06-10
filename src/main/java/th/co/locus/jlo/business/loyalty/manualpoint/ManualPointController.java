package th.co.locus.jlo.business.loyalty.manualpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.engine.bean.EngineCallModelBean;
import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.manualpoint.bean.ManualPointModelBean;
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
import th.co.locus.jlo.engine.client.EngineClient;
import th.co.locus.jlo.engine.client.response.ProcessTransactionResponse;
import th.co.locus.jlo.engine.client.response.ResponseMessage;

@Api(value = "API for Loyalty manaul point adjust", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/manualPoint")
public class ManualPointController extends BaseController{
	
	@Autowired
	private ManualPointService manualPointService;
	
	@Autowired
	private EngineClient engineClient;

	@WritePermission
	@ApiOperation(value = "Create or Update Loyalty manual adjust point -> txnId ")
	@PostMapping(value = "/saveManualPoint", produces = "application/json")
	public ApiResponse<ManualPointModelBean> saveManualPoint(@RequestBody ApiRequest<ManualPointModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<ManualPointModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		serviceResult = manualPointService.insertManualPoint(request.getData());
		
		/*
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = manualPointService.updateManualPoint(request.getData());
		} else {
			serviceResult = manualPointService.insertManualPoint(request.getData());
		}
		*/
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} 
		return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
	}
	
	@ReadPermission
	@ApiOperation(value = "Search Loyalty manual adjust point")
	@PostMapping(value = "/searchManualPoint", produces = "application/json")
	public ApiPageResponse<List<ManualPointModelBean>> seachManualPoint(@RequestBody ApiPageRequest<ManualPointCriteriaModelBean> request) {
	
		
		StringUtil.nullifyObject(request.getData());
		
		request.getData().setBuId(getBuId()); //if need some bu_id
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ManualPointModelBean>> serviceResult = manualPointService.seachManualPoint(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
		
	}

	@ApiOperation(value = "Call engine for process transaction")
	@PostMapping(value = "/callEngine", produces = "application/json")
	public ApiResponse<ManualPointModelBean> callEngine(@RequestBody ApiRequest<EngineCallModelBean> request) {
		ResponseMessage<ProcessTransactionResponse> response = engineClient
				.processTransaction(request.getData().getTxnId());
		if (response != null && response.isSuccess()) {
			ServiceResult<ManualPointModelBean> serviceResult = manualPointService
					.findManualPointById(request.getData().getTxnId());
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail(response.getStatus().getErrorCode(), response.getStatus().getErrorMessage());
		}
	}

}
