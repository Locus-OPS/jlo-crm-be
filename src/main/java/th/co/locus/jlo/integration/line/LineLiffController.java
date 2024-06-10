package th.co.locus.jlo.integration.line;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.loyalty.engine.EngineTxnService;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionRedeemModelBean;
import th.co.locus.jlo.business.redemption.MemberRedemptionService;
import th.co.locus.jlo.business.redemption.bean.RedemptionMemberInfoModelBean;
import th.co.locus.jlo.business.redemption.bean.RedemptionRewardModelBean;
import th.co.locus.jlo.business.redemption.bean.SearchRedemptionRewardModelBean;
import th.co.locus.jlo.business.redemption.bean.SubmitRedeemModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.integration.line.bean.LineMemberInfoModelBean;
import th.co.locus.jlo.integration.line.bean.LineMemberRegisterModelBean;
import th.co.locus.jlo.integration.line.bean.LineRedeemHistoryModelBean;

@RestController
@RequestMapping("api/line")
public class LineLiffController extends BaseController {
	
	@Autowired
	private MemberRedemptionService memberRedemptionService;
	
	@Autowired
	private EngineTxnService engineTxnService;
	
	@Autowired
	private LineService lineService;
	
	@PostMapping(value = "/register", produces = "application/json")
	public ApiResponse<Long> register(@RequestBody ApiRequest<LineMemberRegisterModelBean> request) {
		ServiceResult<Long> serviceResult = lineService.registerLineToMember(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	}
	
	@PostMapping(value = "/checkLineRegister", produces = "application/json")
	public ApiResponse<Long> checkLineRegister(@RequestBody ApiRequest<String> request) {
		ServiceResult<Long> serviceResult = lineService.checkLineRegister(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	}
	
	@PostMapping(value = "/getMemberInfo", produces = "application/json")
	public ApiResponse<LineMemberInfoModelBean> getMemberInfo(@RequestBody ApiRequest<Long> request) {
		ServiceResult<LineMemberInfoModelBean> serviceResult = lineService.getMemberInfo(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	}
	
	@PostMapping(value = "/searchReward", produces = "application/json")
	public ApiPageResponse<List<RedemptionRewardModelBean>> searchReward(@RequestBody ApiPageRequest<Long> request) {
		PageRequest pageRequest = getPageRequest(request);
		SearchRedemptionRewardModelBean param = new SearchRedemptionRewardModelBean();
		param.setMemberId(request.getData());
		param.setDisplayType("Y");
		param.setRedeemMethod("P");
		ServiceResult<Page<RedemptionRewardModelBean>> serviceResult = memberRedemptionService.searchReward(param, pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		}
		return ApiPageResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	}
	
	@PostMapping(value = "/getRedeemHistory", produces = "application/json")
	public ApiResponse<List<LineRedeemHistoryModelBean>> getRedeemHistory(@RequestBody ApiRequest<Long> request) {
		ServiceResult<List<LineRedeemHistoryModelBean>> serviceResult = lineService.getRedeemHistory(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult());
		}
		return ApiPageResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	}
	
	@PostMapping(value = "/submitRedeem", produces = "application/json")
	public ApiResponse<RedemptionMemberInfoModelBean> submitRedeem(@RequestBody ApiRequest<SubmitRedeemModelBean> request) {
		redeem(request.getData());
		ServiceResult<RedemptionMemberInfoModelBean> serviceResult = memberRedemptionService.getMemberInfo(request.getData().getMemberId());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	private void redeem(SubmitRedeemModelBean bean) {
		for (RedemptionRewardModelBean reward : bean.getRewardList()) {
			TransactionRedeemModelBean redeem = new TransactionRedeemModelBean();
			redeem.setMemberId(bean.getMemberId());			
			redeem.setProductCode(reward.getProductCode());
			redeem.setRequestCash(reward.getUseCash());
			redeem.setRequestPoint(reward.getUsePoint());
			redeem.setQuantity(reward.getQuantity());
			redeem.setRedeemMethod(reward.getRedeemMethodType());
			engineTxnService.redeem(redeem);
		}
	}

}
