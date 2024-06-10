package th.co.locus.jlo.business.loyalty.program;

import java.util.List;

import th.co.locus.jlo.business.loyalty.program.bean.CardTierModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.LocationBasePointModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.PrivilegeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramAttributeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramPointTypeModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramShopModelBean;
import th.co.locus.jlo.business.loyalty.program.bean.ProgramTierModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

public interface ProgramService {
	
	/* ################################ Program ############################### */
	public ServiceResult<Page<ProgramModelBean>> getProgramList(ProgramCriteriaModelBean criteria,
			PageRequest pageRequest);
	public ServiceResult<ProgramModelBean> createProgram(ProgramModelBean bean);
	public ServiceResult<ProgramModelBean> updateProgram(ProgramModelBean bean);
	public ServiceResult<Integer> deleteProgram(ProgramModelBean bean);
	
	/*################################ Program Attribute Group ###############################*/
	public ServiceResult<List<ProgramAttributeModelBean>> getAttributeGroupList(String reqData);
	
	/*################################ Program	Attribute ###############################*/	
	public ServiceResult<Page<ProgramAttributeModelBean>> getAttributeList(ProgramAttributeModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ProgramAttributeModelBean> createAttribute(ProgramAttributeModelBean bean);
	public ServiceResult<ProgramAttributeModelBean> updateAttribute(ProgramAttributeModelBean bean);
	public ServiceResult<Integer> deleteAttribute(ProgramAttributeModelBean bean);
	
	/*################################ Program	Tier ###############################*/	
	public ServiceResult<Page<ProgramTierModelBean>> getTierList(ProgramTierModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ProgramTierModelBean> getPrimaryTier(ProgramTierModelBean criteria);
	public ServiceResult<ProgramTierModelBean> createTier(ProgramTierModelBean bean);
	public ServiceResult<ProgramTierModelBean> updateTier(ProgramTierModelBean bean);
	public ServiceResult<ProgramTierModelBean> removePrimaryFlagTier(ProgramTierModelBean bean);
	public ServiceResult<Integer> deleteTier(ProgramTierModelBean bean);
	
	/*################################ Location Base Point ###############################*/	
	public ServiceResult<Page<LocationBasePointModelBean>> getLocationBasePointList(LocationBasePointModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Page<LocationBasePointModelBean>> getNotSelectedLocationBasePointList(LocationBasePointModelBean criteria, PageRequest pageRequest);
	public ServiceResult<Integer> createLocationBasePoint(LocationBasePointModelBean[] bean);
	public ServiceResult<LocationBasePointModelBean> updateLocationBasePoint(LocationBasePointModelBean bean);
	public ServiceResult<Integer> deleteLocationBasePoint(LocationBasePointModelBean bean);

	/*################################ Card Tier ###############################*/	
	public ServiceResult<Page<CardTierModelBean>> getCardTierList(CardTierModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<CardTierModelBean>> getCardTierListWithoutPaging(CardTierModelBean criteria);
	public ServiceResult<CardTierModelBean> createCardTier(CardTierModelBean bean);
	public ServiceResult<CardTierModelBean> updateCardTier(CardTierModelBean bean);
	public ServiceResult<CardTierModelBean> removePrimaryFlagCardTier(CardTierModelBean bean);
	public ServiceResult<Integer> deleteCardTier(CardTierModelBean bean);
	
	/*################################ Privilege ###############################*/	
	public ServiceResult<Page<PrivilegeModelBean>> getPrivilegeList(PrivilegeModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PrivilegeModelBean> createPrivilege(PrivilegeModelBean bean);
	public ServiceResult<PrivilegeModelBean> updatePrivilege(PrivilegeModelBean bean);
	public ServiceResult<Integer> deletePrivilege(PrivilegeModelBean bean);
	
	/*################################ Point Type ###############################*/	
	public ServiceResult<Page<ProgramPointTypeModelBean>> getPointTypeList(ProgramPointTypeModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ProgramPointTypeModelBean> getPrimaryPointType(ProgramPointTypeModelBean criteria);
	public ServiceResult<ProgramPointTypeModelBean> createPointType(ProgramPointTypeModelBean bean);
	public ServiceResult<ProgramPointTypeModelBean> updatePointType(ProgramPointTypeModelBean bean);
	public ServiceResult<ProgramPointTypeModelBean> removePrimaryFlagPointType(ProgramPointTypeModelBean bean);
	public ServiceResult<Integer> deletePointType(ProgramPointTypeModelBean bean);
	
	/*################################ Program ###############################*/
	public ServiceResult<Page<ProgramShopModelBean>> getShopList(ProgramShopModelBean criteria, PageRequest pageRequest);
	public ServiceResult<CardTierModelBean> getPrimaryCardTier(CardTierModelBean criteria);
	public ServiceResult<Integer> insertProgramShop(ProgramShopModelBean[] bean);
	public ServiceResult<ProgramShopModelBean> updateShop(ProgramShopModelBean bean);
	public ServiceResult<Integer> deleteShop(ProgramShopModelBean bean);
	public ServiceResult<Page<ShopModelBean>> getShopMasterList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<List<ShopModelBean>> getShopMasterList(ShopModelBean bean);
	public ServiceResult<List<SelectorModelBean>> getTableList(String tableSchema, String tableNamePrefix);
	public ServiceResult<List<SelectorModelBean>> getColumnList(String tableSchema, String tableName);
}
