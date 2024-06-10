package th.co.locus.jlo.business.loyalty.reward;

import java.util.List;

import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductOnHandModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedeemMethodModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedemptionTransactionModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.reward.bean.RewardModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface RewardService {
	
	public ServiceResult<Page<RewardModelBean>> getRewardList(SearchProductCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<RewardModelBean>> getRewardList(SearchProductCriteriaModelBean criteria);
	public ServiceResult<RewardModelBean> createReward(RewardModelBean bean);
	public ServiceResult<RewardModelBean> updateReward(RewardModelBean bean);
	public void updateRewardImage(String fileName, Long productId);

	public ServiceResult<Page<LoyaltyProductOnHandModelBean>> getRewardOnHandList(LoyaltyProductOnHandModelBean criteria, PageRequest pageRequest);
	public ServiceResult<LoyaltyProductOnHandModelBean> createRewardOnHand(LoyaltyProductOnHandModelBean bean);
	public ServiceResult<LoyaltyProductOnHandModelBean> updateRewardOnHand(LoyaltyProductOnHandModelBean bean);
	public ServiceResult<Integer> deleteRewardOnHand(LoyaltyProductOnHandModelBean bean);
	
	public ServiceResult<Long> getRewardBalance(Long productId);
	public ServiceResult<Long> getRewardRedemptionTransaction(Long productId);

	public ServiceResult<Page<LoyaltyProductRedeemMethodModelBean>> getRedeemMethodList(LoyaltyProductRedeemMethodModelBean criteria, PageRequest pageRequest);
	public ServiceResult<LoyaltyProductRedeemMethodModelBean> createRedeemMethod(LoyaltyProductRedeemMethodModelBean bean);
	public ServiceResult<LoyaltyProductRedeemMethodModelBean> updateRedeemMethod(LoyaltyProductRedeemMethodModelBean bean);
	public ServiceResult<Integer> deleteRedeemMethod(LoyaltyProductRedeemMethodModelBean bean);

	public ServiceResult<Page<LoyaltyProductRedemptionTransactionModelBean>> getRedemptionTransactionList(
			LoyaltyProductRedemptionTransactionModelBean criteria, PageRequest pageRequest);
}
