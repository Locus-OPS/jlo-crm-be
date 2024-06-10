package th.co.locus.jlo.business.loyalty.promotion;

import java.util.List;

import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberMasterData;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModalModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionAttributeModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionMemberModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionShopModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleActionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface PromotionService {
	
	/*################################ Program ###############################*/
	public ServiceResult<Page<PromotionModelBean>> getPromotionList(PromotionModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PromotionModelBean> createPromotion(PromotionModelBean bean);
	public ServiceResult<PromotionModelBean> updatePromotion(PromotionModelBean bean);
	public ServiceResult<Integer> deletePromotion(PromotionModelBean bean);
	public ServiceResult<Integer> updatePromotionDeleteFlag(PromotionModelBean bean);
	public ServiceResult<PromotionModelBean> findBasePromotionById(PromotionModelBean bean);
	
	
	/*################################ Rule ###############################*/
	public ServiceResult<Page<RuleModelBean>> getRuleList(RuleModelBean criteria, PageRequest pageRequest);
	public ServiceResult<RuleModelBean> createRule(RuleModelBean bean);
	public ServiceResult<RuleModelBean> updateRule(RuleModelBean bean);
	public ServiceResult<Boolean> deleteRule(RuleModelBean bean);
	

			/*################################ Rule Sequence ###############################*/
			public ServiceResult<Long> getNextSeq(RuleModelBean bean);
			public ServiceResult<Boolean> updateSeq(RuleModelBean bean);
			public ServiceResult<Integer> updateSeqBeforeDelete(RuleModelBean bean);
			
			/*################################ Rule Criteria ###############################*/
			public ServiceResult<Page<RuleCriteriaModelBean>> getRuleCriteriaList(RuleCriteriaModelBean criteria, PageRequest pageRequest);
			public ServiceResult<RuleCriteriaModelBean> createRuleCriteria(RuleCriteriaModelBean bean);
			public ServiceResult<RuleCriteriaModelBean> updateRuleCriteria(RuleCriteriaModelBean bean);
			public ServiceResult<Boolean> deleteRuleCriteria(RuleCriteriaModelBean bean);
			
			/*################################ Rule Action ###############################*/
			public ServiceResult<Page<RuleActionModelBean>> getRuleActionList(RuleActionModelBean criteria, PageRequest pageRequest);
			public ServiceResult<RuleActionModelBean> createRuleAction(RuleActionModelBean bean);
			public ServiceResult<RuleActionModelBean> updateRuleAction(RuleActionModelBean bean);
			public ServiceResult<Boolean> deleteRuleAction(RuleActionModelBean bean);
	
	/*################################ Product ###############################*/
	public ServiceResult<Page<ProductModelBean>> getProductList(ProductModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Integer> insertPromotionProduct(ProductModelBean[] bean);
	public ServiceResult<ProductModelBean> updateProduct(ProductModelBean bean);
	public ServiceResult<Integer> deleteProduct(ProductModelBean bean);
	public ServiceResult<Page<ProductModalModelBean>> getAllProductList(ProductModalModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<ProductModalModelBean>> getProductMasterList(ProductModalModelBean bean);

	
	/*################################ Member ###############################*/
	public ServiceResult<Page<PromotionMemberModelBean>> getMemberList(PromotionMemberModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Integer> insertPromotionMember(PromotionMemberModelBean[] bean);
	public ServiceResult<PromotionMemberModelBean> updateMember(PromotionMemberModelBean bean);
	public ServiceResult<Integer> deleteMember(PromotionMemberModelBean bean);
	public ServiceResult<Page<MemberData>> getAllMemberList(MemberData criteria, PageRequest pageRequest);
	public ServiceResult<Page<MemberMasterData>> getMemberMasterList(MemberMasterData criteria, PageRequest pageRequest);
	public ServiceResult<List<MemberData>> getMemberMasterList(MemberData bean);

	/*################################ Program ###############################*/
	public ServiceResult<Page<PromotionShopModelBean>> getShopList(PromotionShopModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Integer> insertPromotionShop(PromotionShopModelBean[] bean);
	public ServiceResult<PromotionShopModelBean> updateShop(PromotionShopModelBean bean);
	public ServiceResult<Integer> deleteShop(PromotionShopModelBean bean);
	public ServiceResult<Page<ShopModelBean>> getAllShopList(ShopModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Page<ShopModelBean>> getShopMasterList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<ShopModelBean>> getShopMasterList(ShopModelBean bean);

	/*################################ Attribute ###############################*/
	public ServiceResult<Page<PromotionAttributeModelBean>> getPromotionAttributeList(PromotionAttributeModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PromotionAttributeModelBean> createPromotionAttribute(PromotionAttributeModelBean bean);
	public ServiceResult<PromotionAttributeModelBean> updatePromotionAttribute(PromotionAttributeModelBean bean);
	public ServiceResult<Integer> deletePromotionAttribute(PromotionAttributeModelBean bean);
	
}
