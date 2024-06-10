package th.co.locus.jlo.business.redemption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.engine.EngineTxnService;
import th.co.locus.jlo.business.loyalty.engine.bean.TransactionRedeemModelBean;
import th.co.locus.jlo.business.loyalty.transaction.bean.TransactionModelBean;
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
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;

@Api(value = "API for Member Redemption", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/redemption")
public class MemberRedemptionController extends BaseController {
	
	@Autowired
	private MemberRedemptionService memberRedemptionService;
	
	@Autowired
	private EngineTxnService engineTxnService;
	
	@ReadPermission
	@ApiOperation(value = "Get Member Info")
	@PostMapping(value = "/getMemberInfo", produces = "application/json")
	public ApiResponse<RedemptionMemberInfoModelBean> getMemberInfo(@RequestBody ApiRequest<Long> request) {
		ServiceResult<RedemptionMemberInfoModelBean> serviceResult = memberRedemptionService.getMemberInfo(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ReadPermission
	@ApiOperation(value = "Search Reward for Redemption")
	@PostMapping(value = "/searchReward", produces = "application/json")
	public ApiPageResponse<List<RedemptionRewardModelBean>> searchReward(@RequestBody ApiPageRequest<SearchRedemptionRewardModelBean> request) {
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RedemptionRewardModelBean>> serviceResult = memberRedemptionService.searchReward(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			List<RedemptionRewardModelBean> rewaardList = serviceResult.getResult().getContent();
			return ApiPageResponse.success(rewaardList, serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Submit Redeem Reward")
	@PostMapping(value = "/submitRedeem", produces = "application/json")
	public ApiResponse<RedemptionMemberInfoModelBean> submitRedeem(@RequestBody ApiRequest<SubmitRedeemModelBean> request) {
		
		ServiceResult<TransactionModelBean> redeemResult = this.redeem(request.getData());
		if (!redeemResult.isSuccess()) {
			return ApiPageResponse.fail(redeemResult.getResponseCode(), redeemResult.getResponseDescription());
		}
		ServiceResult<RedemptionMemberInfoModelBean> serviceResult = memberRedemptionService.getMemberInfo(request.getData().getMemberId());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	private ServiceResult<TransactionModelBean> redeem(SubmitRedeemModelBean submitRedeemModelBean) {
		for (RedemptionRewardModelBean reward : submitRedeemModelBean.getRewardList()) {
			TransactionRedeemModelBean redeem = new TransactionRedeemModelBean();
			redeem.setMemberId(submitRedeemModelBean.getMemberId());			
			redeem.setProductCode(reward.getProductCode());
			redeem.setRequestCash(reward.getUseCash());
			redeem.setRequestPoint(reward.getUsePoint());
			redeem.setQuantity(reward.getQuantity());
			redeem.setRedeemMethod(reward.getRedeemMethodType());
			ServiceResult<TransactionModelBean> redeemResult = engineTxnService.redeem(redeem);
			if (!redeemResult.isSuccess()) {
				return redeemResult;
			}
		}
		return new ServiceResult<>(true);
	}

}
