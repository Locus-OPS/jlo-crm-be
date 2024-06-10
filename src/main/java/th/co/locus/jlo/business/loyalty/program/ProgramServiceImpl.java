package th.co.locus.jlo.business.loyalty.program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

@Service
public class ProgramServiceImpl extends BaseService implements ProgramService {

	/* ################################ Program ############################### */
	@Override
	public ServiceResult<Page<ProgramModelBean>> getProgramList(ProgramCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getProgramList", criteria, pageRequest));
	}

	@Override
	@Transactional
	public ServiceResult<ProgramModelBean> createProgram(ProgramModelBean bean) {
		int result = commonDao.update("program.createProgram", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findProgramById", bean));
		}
		return fail();
	}

	@Override
	@Transactional
	public ServiceResult<ProgramModelBean> updateProgram(ProgramModelBean bean) {
		int result = commonDao.update("program.updateProgram", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findProgramById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteProgram(ProgramModelBean bean) {
		return success(commonDao.delete("program.deleteProgram", bean));
	}

	/*
	 * ################################ Program Attribute Group
	 * ###############################
	 */
	@Override
	public ServiceResult<List<ProgramAttributeModelBean>> getAttributeGroupList(String reqData) {
		return success(commonDao.selectList("program.getAttributeGroupList", Map.of("programId", reqData)));

	}

	/*
	 * ################################ Program Attribute
	 * ###############################
	 */
	@Override
	public ServiceResult<Page<ProgramAttributeModelBean>> getAttributeList(ProgramAttributeModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getAttributeList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<ProgramAttributeModelBean> createAttribute(ProgramAttributeModelBean bean) {
		int result = commonDao.update("program.createAttribute", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findAttributeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProgramAttributeModelBean> updateAttribute(ProgramAttributeModelBean bean) {
		int result = commonDao.update("program.updateAttribute", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findAttributeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteAttribute(ProgramAttributeModelBean bean) {
		return success(commonDao.delete("program.deleteAttribute", bean));
	}

	/*
	 * ################################ Program Tier ###############################
	 */
	@Override
	public ServiceResult<Page<ProgramTierModelBean>> getTierList(ProgramTierModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getTierList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<ProgramTierModelBean> getPrimaryTier(ProgramTierModelBean criteria) {
		return success(commonDao.selectOne("program.getTierList", criteria));
	}

	@Override
	public ServiceResult<ProgramTierModelBean> createTier(ProgramTierModelBean bean) {
		int result = commonDao.update("program.createTier", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findTierById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProgramTierModelBean> updateTier(ProgramTierModelBean bean) {
		int result = commonDao.update("program.updateTier", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findTierById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProgramTierModelBean> removePrimaryFlagTier(ProgramTierModelBean bean) {
		int result = commonDao.update("program.removePrimaryFlagTier", bean);
		if (result > 0) {
			return success(bean);
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteTier(ProgramTierModelBean bean) {
		return success(commonDao.delete("program.deleteTier", bean));
	}

	/*
	 * ################################ Location Base Point
	 * ###############################
	 */
	@Override
	public ServiceResult<Page<LocationBasePointModelBean>> getLocationBasePointList(LocationBasePointModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getLocationBasePointList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Page<LocationBasePointModelBean>> getNotSelectedLocationBasePointList(
			LocationBasePointModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getNotSelectedLocationBasePointList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Integer> createLocationBasePoint(LocationBasePointModelBean[] bean) {

		Map<String, List<LocationBasePointModelBean>> lbpMap = new HashMap<String, List<LocationBasePointModelBean>>();
		List<LocationBasePointModelBean> lbpList = new ArrayList<>(Arrays.asList(bean));
		lbpMap.put("lbpList", lbpList);

		return success(commonDao.update("program.createLocationBasePoint", lbpMap));
	}

	@Override
	public ServiceResult<LocationBasePointModelBean> updateLocationBasePoint(LocationBasePointModelBean bean) {
		int result = commonDao.update("program.updateLocationBasePoint", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findLocationBasePointById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteLocationBasePoint(LocationBasePointModelBean bean) {
		return success(commonDao.delete("program.deleteLocationBasePoint", bean));
	}

	/* ################################ Card Tier ############################### */
	@Override
	public ServiceResult<Page<CardTierModelBean>> getCardTierList(CardTierModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getCardTierList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<List<CardTierModelBean>> getCardTierListWithoutPaging(CardTierModelBean criteria) {
		return success(commonDao.selectList("program.getCardTierList", criteria));
	}
	
	@Override
	public ServiceResult<CardTierModelBean> getPrimaryCardTier(CardTierModelBean criteria) {
		return success(commonDao.selectOne("program.getCardTierList", criteria));
	}

	@Override
	public ServiceResult<CardTierModelBean> createCardTier(CardTierModelBean bean) {
		int result = commonDao.update("program.createCardTier", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findCardTierById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<CardTierModelBean> updateCardTier(CardTierModelBean bean) {
		int result = commonDao.update("program.updateCardTier", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findCardTierById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<CardTierModelBean> removePrimaryFlagCardTier(CardTierModelBean bean) {
		int result = commonDao.update("program.removePrimaryFlagCardTier", bean);
		if (result > 0) {
			return success(bean);
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteCardTier(CardTierModelBean bean) {
		return success(commonDao.delete("program.deleteCardTier", bean));
	}

	/* ################################ Privilege ############################### */
	@Override
	public ServiceResult<Page<PrivilegeModelBean>> getPrivilegeList(PrivilegeModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getPrivilegeList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<PrivilegeModelBean> createPrivilege(PrivilegeModelBean bean) {
		int result = commonDao.update("program.createPrivilege", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findPrivilegeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PrivilegeModelBean> updatePrivilege(PrivilegeModelBean bean) {
		int result = commonDao.update("program.updatePrivilege", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findPrivilegeById", bean));
		}
		return fail();
	}
 
	@Override
	public ServiceResult<Integer> deletePrivilege(PrivilegeModelBean bean) {
		return success(commonDao.delete("program.deletePrivilege", bean));
	}

	/*
	 * ################################ Point Type ###############################
	 */
	@Override
	public ServiceResult<Page<ProgramPointTypeModelBean>> getPointTypeList(ProgramPointTypeModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getPointTypeList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<ProgramPointTypeModelBean> getPrimaryPointType(ProgramPointTypeModelBean criteria) {
		return success(commonDao.selectOne("program.getPointTypeList", criteria));
	}

	@Override
	public ServiceResult<ProgramPointTypeModelBean> createPointType(ProgramPointTypeModelBean bean) {
		int result = commonDao.update("program.createPointType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findPointTypeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProgramPointTypeModelBean> updatePointType(ProgramPointTypeModelBean bean) {
		int result = commonDao.update("program.updatePointType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findPointTypeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProgramPointTypeModelBean> removePrimaryFlagPointType(ProgramPointTypeModelBean bean) {
		int result = commonDao.update("program.removePrimaryFlagPointType", bean);
		if (result > 0) {
			return success(bean);
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deletePointType(ProgramPointTypeModelBean bean) {
		return success(commonDao.delete("program.deletePointType", bean));
	}

	/* ################################ Program ############################### */
	@Override
	public ServiceResult<Page<ProgramShopModelBean>> getShopList(ProgramShopModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getShopList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<Integer> insertProgramShop(ProgramShopModelBean[] bean) {

		Map<String, List<ProgramShopModelBean>> programShopMap = new HashMap<String, List<ProgramShopModelBean>>();
		List<ProgramShopModelBean> programShopList = new ArrayList<>(Arrays.asList(bean));
		programShopMap.put("programShopList", programShopList);

		return success(commonDao.update("program.insertProgramShop", programShopMap));
	}

	@Override
	public ServiceResult<ProgramShopModelBean> updateShop(ProgramShopModelBean bean) {
		int result = commonDao.update("program.updateShop", bean);
		if (result > 0) {
			return success(commonDao.selectOne("program.findShopById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteShop(ProgramShopModelBean bean) {
		int result = commonDao.delete("program.deleteShop", bean);
		if (result > 0) {
			return success(result);
		}
		return fail();
	}

	@Override
	public ServiceResult<Page<ShopModelBean>> getShopMasterList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("program.getShopMasterList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<List<ShopModelBean>> getShopMasterList(ShopModelBean bean) {
		return success(commonDao.selectList("program.getShopMasterList", bean));
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getTableList(String tableSchema, String tableNamePrefix) {
		return success(commonDao.selectList("program.getTableList",
				Map.of("tableSchema", tableSchema, "tableNamePrefix", tableNamePrefix)));
	}

	@Override
	public ServiceResult<List<SelectorModelBean>> getColumnList(String tableSchema, String tableName) {
		return success(commonDao.selectList("program.getColumnList",
				Map.of("tableSchema", tableSchema, "tableName", tableName)));
	}
}
