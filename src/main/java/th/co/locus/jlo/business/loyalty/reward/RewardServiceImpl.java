package th.co.locus.jlo.business.loyalty.reward;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductOnHandModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedeemMethodModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.LoyaltyProductRedemptionTransactionModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.reward.bean.RewardModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class RewardServiceImpl extends BaseService implements RewardService {

	@Override
	public ServiceResult<Page<RewardModelBean>> getRewardList(SearchProductCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("reward.getRewardList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<List<RewardModelBean>> getRewardList(SearchProductCriteriaModelBean criteria) {
		return success(commonDao.selectList("reward.getRewardList", criteria));
	}

	@Override
	public ServiceResult<RewardModelBean> createReward(RewardModelBean bean) {
		int result = commonDao.update("reward.createReward", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRewardById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<RewardModelBean> updateReward(RewardModelBean bean) {
		int result = commonDao.update("reward.updateReward", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRewardById", bean));
		}
		return fail();
	}

	@Override
	public void updateRewardImage(String fileName, Long productId) {
		commonDao.update("reward.updateRewardImage", Map.of("productImgPath", fileName, "productId", productId));			
	}
	
	@Override
	public ServiceResult<Page<LoyaltyProductOnHandModelBean>> getRewardOnHandList(
			LoyaltyProductOnHandModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("reward.getRewardOnHandList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<LoyaltyProductOnHandModelBean> createRewardOnHand(LoyaltyProductOnHandModelBean bean) {
		int result = commonDao.update("reward.createRewardOnHand", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRewardOnHandById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<LoyaltyProductOnHandModelBean> updateRewardOnHand(LoyaltyProductOnHandModelBean bean) {
		int result = commonDao.update("reward.updateRewardOnHand", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRewardOnHandById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteRewardOnHand(LoyaltyProductOnHandModelBean bean) {
		return success(commonDao.delete("reward.deleteRewardOnHand", bean));
	}
	
	@Override
	public ServiceResult<Long> getRewardBalance(Long productId) {
		return success(commonDao.selectOne("reward.getRewardBalance", productId));
	}

	@Override
	public ServiceResult<Long> getRewardRedemptionTransaction(Long productId) {
		return success(commonDao.selectOne("reward.getRewardRedemptionTransaction", productId));
	}
	
	@Override
	public ServiceResult<Page<LoyaltyProductRedeemMethodModelBean>> getRedeemMethodList(
			LoyaltyProductRedeemMethodModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("reward.getRedeemMethodList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<LoyaltyProductRedeemMethodModelBean> createRedeemMethod(
			LoyaltyProductRedeemMethodModelBean bean) {
		int result = commonDao.update("reward.createRedeemMethod", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRedeemMethodById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<LoyaltyProductRedeemMethodModelBean> updateRedeemMethod(
			LoyaltyProductRedeemMethodModelBean bean) {
		int result = commonDao.update("reward.updateRedeemMethod", bean);
		if (result > 0) {
			return success(commonDao.selectOne("reward.findRedeemMethodById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteRedeemMethod(LoyaltyProductRedeemMethodModelBean bean) {
		return success(commonDao.delete("reward.deleteRedeemMethod", bean));
	}

	@Override
	public ServiceResult<Page<LoyaltyProductRedemptionTransactionModelBean>> getRedemptionTransactionList(
			LoyaltyProductRedemptionTransactionModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("reward.getRedemptionTransactionList", criteria, pageRequest));
	}
	
}
