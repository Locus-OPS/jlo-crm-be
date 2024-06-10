package th.co.locus.jlo.business.loyalty.promotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.customer.bean.MemberMasterData;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramAttributeModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.ActionQueryBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModalModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionAttributeModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionMemberModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionShopModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.PromotionUtils;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleActionModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleModelBean;
import th.co.locus.jlo.business.loyalty.promotion.bean.RuleQueryBean;
import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.constant.JLOConstant;
import th.co.locus.jlo.common.exception.BusinessRuntimeException;

@Service
public class PromotionServiceImpl extends BaseService implements PromotionService {

	/*################################ Program ###############################*/
	@Override
	public ServiceResult<Page<PromotionModelBean>> getPromotionList(PromotionModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getPromotionList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<PromotionModelBean> createPromotion(PromotionModelBean bean) {
		int result = commonDao.update("promotion.createPromotion", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findPromotionById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PromotionModelBean> updatePromotion(PromotionModelBean bean) {
		int result = commonDao.update("promotion.updatePromotion", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findPromotionById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deletePromotion(PromotionModelBean bean) {
		int result = commonDao.delete("promotion.deletePromotion", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> updatePromotionDeleteFlag(PromotionModelBean bean) {
		int result = commonDao.update("promotion.updatePromotionDeleteFlag", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<PromotionModelBean> findBasePromotionById(PromotionModelBean bean) {
		return success(commonDao.selectOne("promotion.findPromotionById", bean));
	}
	
	/*################################ Rule ###############################*/
	@Override
	public ServiceResult<Page<RuleModelBean>> getRuleList(RuleModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getRuleList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<RuleModelBean> createRule(RuleModelBean bean) {
		long seq = commonDao.selectOne("promotion.getNextSeq", bean);
		bean.setSeq(seq);
		
		int result = commonDao.update("promotion.createRule", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findRuleById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<RuleModelBean> updateRule(RuleModelBean bean) {
		int result = commonDao.update("promotion.updateRule", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findRuleById", bean));
		}
		return fail();
	}

	@Override
	@Transactional
	public ServiceResult<Boolean> deleteRule(RuleModelBean bean) {
		
		commonDao.update("promotion.updateSeqBeforeDelete", bean);
		
		if(commonDao.delete("promotion.deleteRule", bean) == 0)
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_DELETED);
		
		return success(Boolean.TRUE); 
	}
	
	/*################################ Rule Sequence ###############################*/
	@Override
	public ServiceResult<Integer> updateSeqBeforeDelete(RuleModelBean bean) {
		int result = commonDao.update("promotion.updateSeqBeforeDelete", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Long> getNextSeq(RuleModelBean bean) {
		return success(commonDao.selectOne("promotion.getNextSeq"));
	}

	@Override
	@Transactional
	public ServiceResult<Boolean> updateSeq(RuleModelBean bean) {
		
		if (commonDao.update("promotion.updateOldSeq", bean) == 0) 
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED);
		
		if (commonDao.update("promotion.updateNewSeq", bean) == 0) 
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED); 
		
		return success(Boolean.TRUE); 
	}
	
	/*################################ Rule Criteria ###############################*/
	@Override
	public ServiceResult<Page<RuleCriteriaModelBean>> getRuleCriteriaList(RuleCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getRuleCriteriaList", criteria, pageRequest));
	}

	@Override
	@Transactional
	public ServiceResult<RuleCriteriaModelBean> createRuleCriteria(RuleCriteriaModelBean bean) {
		
		setRuleCriteriaExpression(bean);
		
		if (commonDao.update("promotion.createRuleCriteria", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_CREATED); 
		}
		
		updateRuleQueryJoin(bean);
		
		return success(commonDao.selectOne("promotion.findRuleCriteriaById", bean));
	}

	@Override
	@Transactional
	public ServiceResult<RuleCriteriaModelBean> updateRuleCriteria(RuleCriteriaModelBean bean) {
		
		setRuleCriteriaExpression(bean);

		if (commonDao.update("promotion.updateRuleCriteria", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED); 
		}
		
		updateRuleQueryJoin(bean);
		
		return success(commonDao.selectOne("promotion.findRuleCriteriaById", bean));
	}

	@Override
	@Transactional
	public ServiceResult<Boolean> deleteRuleCriteria(RuleCriteriaModelBean bean) {
		if (commonDao.delete("promotion.deleteRuleCriteria", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_DELETED); 
		}
		updateRuleQueryJoin(bean);
		return success(Boolean.TRUE);
	}
	
	@Transactional
	private void updateRuleQueryJoin(RuleCriteriaModelBean bean){
		List<RuleCriteriaModelBean> list = commonDao.selectList("promotion.getRuleCriteriaForRuleBuild", bean);
		
		RuleQueryBean ruleQueryBean = PromotionUtils.getRuleQueryJoin(list);			
		ruleQueryBean.setRuleId(bean.getRuleId());
		
		if(commonDao.update("promotion.updateRuleQueryJoin", ruleQueryBean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED); 
		}
	}
	
	@Transactional
	private void setRuleCriteriaExpression(RuleCriteriaModelBean bean){
		List<String> idList = new ArrayList<>();
		idList.add(bean.getSrcAttr());
		if (StringUtils.isNotEmpty(bean.getDscAttr())) {
			idList.add(bean.getDscAttr());
		}
		
		bean.setIdList(idList);
		
		List<ProgramAttributeModelBean> attrList = commonDao.selectList("promotion.getAttrByIdList", bean);
		
		Optional<ProgramAttributeModelBean> src = 
				attrList.stream().filter(attr -> Long.toString(attr.getAttrId()).equals(bean.getSrcAttr()) && Long.toString(attr.getAttrGroupId()).equals(bean.getSrcObj())).findFirst();
		if (!src.isPresent()) {
			throw new BusinessRuntimeException(JLOConstant.NO_PROGRAM_ATTR_LIST_FOUND);
		}
		Optional<ProgramAttributeModelBean> dsc = 
				attrList.stream().filter(attr -> Long.toString(attr.getAttrId()).equals(bean.getDscAttr()) && Long.toString(attr.getAttrGroupId()).equals(bean.getDscObj())).findFirst();
		
		bean.setExp(PromotionUtils.getCriteriaExpression(bean, src.get(), (dsc.isPresent() ? dsc.get() : null)));
	}
	
	/*################################ Rule Action ###############################*/
	@Override
	public ServiceResult<Page<RuleActionModelBean>> getRuleActionList(RuleActionModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getRuleActionList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<RuleActionModelBean> createRuleAction(RuleActionModelBean bean) {
		if (commonDao.update("promotion.createRuleAction", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_CREATED); 
		}
		RuleActionModelBean createdRuleActionModelBean = commonDao.selectOne("promotion.findRuleActionById", bean);
		updateActionQuery(createdRuleActionModelBean);
		return success(createdRuleActionModelBean);
	}

	@Override
	public ServiceResult<RuleActionModelBean> updateRuleAction(RuleActionModelBean bean) {
		if (commonDao.update("promotion.updateRuleAction", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED); 
		}
		RuleActionModelBean createdRuleActionModelBean = commonDao.selectOne("promotion.findRuleActionById", bean);
		updateActionQuery(createdRuleActionModelBean);
		return success(createdRuleActionModelBean);
	}

	@Override
	public ServiceResult<Boolean> deleteRuleAction(RuleActionModelBean bean) {
		if (commonDao.delete("promotion.deleteRuleAction", bean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_DELETED); 
		}
		return success(Boolean.TRUE);
	}
	
	private void updateActionQuery(RuleActionModelBean ruleActionModelBean){
		RuleActionModelBean obj = commonDao.selectOne("promotion.getRuleActionForActionQueryBuild", ruleActionModelBean);
		
		ActionQueryBean bean = PromotionUtils.getActionQueryExp(ruleActionModelBean.getActionType(), obj);
		ruleActionModelBean.setActionQuery(bean.getActionQuery());
		ruleActionModelBean.setExp(bean.getActionExp());
		
		if (commonDao.update("promotion.updateRuleActionQuery", ruleActionModelBean) == 0) {
			throw new BusinessRuntimeException(JLOConstant.NO_RECORD_FOUND_UPDATED_RULE_ACTION_QUERY); 
		}
	}
	
	/*################################ Product ###############################*/
	@Override
	public ServiceResult<Page<ProductModelBean>> getProductList(ProductModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getProductList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<Integer> insertPromotionProduct(ProductModelBean[] bean) {
		
		Map<String, List<ProductModelBean>> productModalMap = new HashMap<String, List<ProductModelBean>>();
		List<ProductModelBean> productModalList = new ArrayList<>(Arrays.asList(bean));
		productModalMap.put("productModalList", productModalList);
		
		return success(commonDao.update("promotion.insertPromotionProduct", productModalMap));
	}
	
	@Override
	public ServiceResult<ProductModelBean> updateProduct(ProductModelBean bean) {
		int result = commonDao.update("promotion.updateProduct", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findProductById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deleteProduct(ProductModelBean bean) {
		int result = commonDao.delete("promotion.deleteProduct", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<ProductModalModelBean>> getAllProductList(ProductModalModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getAllProductList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<List<ProductModalModelBean>> getProductMasterList(ProductModalModelBean bean) {
		return success(commonDao.selectList("promotion.getProductMasterList", bean));
	}
	
	/*################################ Member ###############################*/
	@Override
	public ServiceResult<Page<PromotionMemberModelBean>> getMemberList(PromotionMemberModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getMemberList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<Integer> insertPromotionMember(PromotionMemberModelBean[] bean) {
		
		Map<String, List<PromotionMemberModelBean>> promotionMemberMap = new HashMap<String, List<PromotionMemberModelBean>>();
		List<PromotionMemberModelBean> promotionMemberList = new ArrayList<>(Arrays.asList(bean));
		promotionMemberMap.put("promotionMemberList", promotionMemberList);
		
		return success(commonDao.update("promotion.insertPromotionMember", promotionMemberMap));
	}
	
	@Override
	public ServiceResult<PromotionMemberModelBean> updateMember(PromotionMemberModelBean bean) {
		int result = commonDao.update("promotion.updateMember", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findMemberById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deleteMember(PromotionMemberModelBean bean) {
		int result = commonDao.delete("promotion.deleteMember", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<MemberData>> getAllMemberList(MemberData criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getAllMemberList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<Page<MemberMasterData>> getMemberMasterList(MemberMasterData criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getMemberMasterList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<List<MemberData>> getMemberMasterList(MemberData bean) {
		return success(commonDao.selectList("promotion.getMemberMasterList", bean));
	}
	
	/*################################ Program ###############################*/
	@Override
	public ServiceResult<Page<PromotionShopModelBean>> getShopList(PromotionShopModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getShopList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<Integer> insertPromotionShop(PromotionShopModelBean[] bean) {
		
		Map<String, List<PromotionShopModelBean>> promotionShopMap = new HashMap<String, List<PromotionShopModelBean>>();
		List<PromotionShopModelBean> promotionShopList = new ArrayList<>(Arrays.asList(bean));
		promotionShopMap.put("promotionShopList", promotionShopList);
		
		return success(commonDao.update("promotion.insertPromotionShop", promotionShopMap));
	}
	
	@Override
	public ServiceResult<PromotionShopModelBean> updateShop(PromotionShopModelBean bean) {
		int result = commonDao.update("promotion.updateShop", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findShopById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deleteShop(PromotionShopModelBean bean) {
		int result = commonDao.delete("promotion.deleteShop", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<ShopModelBean>> getAllShopList(ShopModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getAllShopList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<Page<ShopModelBean>> getShopMasterList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getShopMasterList", criteria, pageRequest));
	}
	
	@Override
	public ServiceResult<List<ShopModelBean>> getShopMasterList(ShopModelBean bean) {
		return success(commonDao.selectList("promotion.getShopMasterList", bean));
	}
	
	
	/*################################ Attribute ###############################*/
	@Override
	public ServiceResult<Page<PromotionAttributeModelBean>> getPromotionAttributeList(PromotionAttributeModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("promotion.getPromotionAttributeList", criteria, pageRequest));
	}	

	@Override
	@Transactional
	public ServiceResult<PromotionAttributeModelBean> createPromotionAttribute(PromotionAttributeModelBean bean) {
		ProgramAttributeModelBean attr = new ProgramAttributeModelBean();
		attr.setAttrName(bean.getPromotionAttr());
		attr.setAttrActiveYn(bean.getActiveFlag());
		attr.setDataType(bean.getPromotionAttrDataTypeId());
		attr.setDefaultValue(bean.getPromotionAttrInitValue());
		attr.setCreatedBy(bean.getCreatedBy());
		attr.setBuId(bean.getBuId());
		commonDao.insert("promotion.createLoyAttribute", attr);
		
		bean.setRefLoyAttrId(attr.getAttrId());
		int result = commonDao.insert("promotion.createPromotionAttribute", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findPromotionAttributeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PromotionAttributeModelBean> updatePromotionAttribute(PromotionAttributeModelBean bean) {
		int result = commonDao.update("promotion.updatePromotionAttribute", bean);
		if (result > 0) {
			return success(commonDao.selectOne("promotion.findPromotionAttributeById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Integer> deletePromotionAttribute(PromotionAttributeModelBean bean) {
		int result = commonDao.delete("promotion.deletePromotionAttribute", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

}
