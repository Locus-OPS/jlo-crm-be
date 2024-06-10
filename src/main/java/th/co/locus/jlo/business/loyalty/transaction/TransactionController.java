package th.co.locus.jlo.business.loyalty.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.loyalty.engine.EngineTxnService;
import th.co.locus.jlo.business.loyalty.engine.bean.EngineCallModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionCancelModelBean;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionEnrollModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.BurnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.CardModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.EarnItemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.PromotionAttrModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchCardCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.SearchTransactionCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
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

@Api(value = "API for Loyalty Campaign Management", consumes = "application/json", produces = "application/json")
@RestController
@Slf4j
@RequestMapping("api/transaction")
public class TransactionController extends BaseController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private EngineTxnService engineTxnService;
	
	@Autowired
	private EngineClient engineClient;
	
	@ReadPermission
	@ApiOperation(value = "Get Transaction List")
	@PostMapping(value = "/getTransactionList", produces = "application/json")
	public ApiPageResponse<List<TransactionModelBean>> getTransactionList(@RequestBody ApiPageRequest<SearchTransactionCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<TransactionModelBean>> serviceResult = transactionService.getTransactionList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Transaction")
	@PostMapping(value = "/saveTransaction", produces = "application/json")
	public ApiResponse<TransactionModelBean> saveTransaction(@RequestBody ApiRequest<TransactionModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<TransactionModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = transactionService.updateTransaction(request.getData());
		} else {
			serviceResult = transactionService.createTransaction(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Earn Item List")
	@PostMapping(value = "/getEarnItemList", produces = "application/json")
	public ApiPageResponse<List<EarnItemModelBean>> getEarnItemList(@RequestBody ApiPageRequest<SearchTransactionCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<EarnItemModelBean>> serviceResult = transactionService.getEarnItemList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Burn Item List")
	@PostMapping(value = "/getBurnItemList", produces = "application/json")
	public ApiPageResponse<List<BurnItemModelBean>> getBurnItemList(@RequestBody ApiPageRequest<SearchTransactionCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<BurnItemModelBean>> serviceResult = transactionService.getBurnItemList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Promoton Attribute List")
	@PostMapping(value = "/getPromotionAttrList", produces = "application/json")
	public ApiPageResponse<List<PromotionAttrModelBean>> getPromotionAttrList(@RequestBody ApiPageRequest<SearchTransactionCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PromotionAttrModelBean>> serviceResult = transactionService.getPromotionAttrList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Card List")
	@PostMapping(value = "/getCardList", produces = "application/json")
	public ApiPageResponse<List<CardModelBean>> getCardList(@RequestBody ApiPageRequest<SearchCardCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<CardModelBean>> serviceResult = transactionService.getCardList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Get Product List")
	@PostMapping(value = "/getProductList", produces = "application/json")
	public ApiPageResponse<List<ProductModelBean>> getProductList(@RequestBody ApiPageRequest<SearchProductCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProductModelBean>> serviceResult = transactionService.getProductList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Enroll Member Transaction")
	@PostMapping(value = "/enrollMember", produces = "application/json")
	public ApiResponse<TransactionModelBean> enrollMember(@RequestBody ApiRequest<TransactionEnrollModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		//set enroll member parameter
		TransactionEnrollModelBean bean = new TransactionEnrollModelBean();
		bean.setMemberId( request.getData().getMemberId() );
		
		ServiceResult<TransactionModelBean> serviceResult = engineTxnService.enrollMember(bean);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail(serviceResult.getResponseDescription());
		}
		
	}
	

	
	@ApiOperation(value = "Cancel Transaction")
	@PostMapping(value = "/cancelTransaction", produces = "application/json")
	public ApiResponse<TransactionModelBean> cancelTransaction(@RequestBody ApiRequest<TransactionModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		
	    //set cancel bean parameter
		TransactionCancelModelBean bean = new TransactionCancelModelBean();
		bean.setTxnId( String.valueOf(request.getData().getTxnId()) );
		
		
		ServiceResult<TransactionModelBean> serviceResult = engineTxnService.cancelTransaction(bean);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail(serviceResult.getResponseDescription());
		}
	}

	@ApiOperation(value = "Call engine for process transaction")
	@PostMapping(value = "/callEngine", produces = "application/json")
	public ApiResponse<TransactionModelBean> callEngine(@RequestBody ApiRequest<EngineCallModelBean> request) {
		ResponseMessage<ProcessTransactionResponse> callEngineResponse = engineClient
				.processTransaction(request.getData().getTxnId());
		log.debug("callEngineResponse : " + callEngineResponse);
		if (callEngineResponse != null && callEngineResponse.isSuccess()) {
			ServiceResult<TransactionModelBean> serviceResult = transactionService
					.getTransactionById(request.getData().getTxnId());
			log.debug(serviceResult.getResult().toString());
			return ApiResponse.success(serviceResult.getResult());
		} else if (callEngineResponse != null && !callEngineResponse.isSuccess()) {
			return ApiResponse.fail(callEngineResponse.getStatus().getErrorCode(),
					callEngineResponse.getStatus().getErrorMessage());
		}
		return ApiResponse.fail();
	}
}
