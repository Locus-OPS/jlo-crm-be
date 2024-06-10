package th.co.locus.jlo.util.selector;

import java.util.List;
import java.util.Map;

import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorCustomModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

public interface SelectorService {

	public ServiceResult<Map<String, List<SelectorModelBean>>> getMultipleCodebookByCodeType(String[] types);
	public ServiceResult<List<SelectorModelBean>> getCodebookByCodeType(String type);
	public ServiceResult<List<SelectorModelBean>> getCodebookByCodeTypeAndParentId(String data);
	public ServiceResult<List<SelectorModelBean>> getBusinessUnit();
	public ServiceResult<List<SelectorModelBean>> getRole();
	public ServiceResult<List<SelectorModelBean>> getPosition();
	public ServiceResult<List<SelectorModelBean>> getCodebookType();
	public ServiceResult<List<SelectorModelBean>> getProgram();
	public ServiceResult<List<SelectorModelBean>> getShopType();
	public ServiceResult<List<SelectorModelBean>> getProductCategory();
	public ServiceResult<List<SelectorModelBean>> getCampaign();
	
	public ServiceResult<List<SelectorModelBean>> getAttrGroup();
	public ServiceResult<List<SelectorModelBean>> getAttr();
	public ServiceResult<List<SelectorModelBean>> getPointType();
	public ServiceResult<List<SelectorModelBean>> getPartnerType();
	public ServiceResult<List<SelectorModelBean>> getParentMenu();
	
	public ServiceResult<List<SelectorModelBean>> getProvince(String parent);
	public ServiceResult<List<SelectorModelBean>> getDistrict(String parent);
	public ServiceResult<List<SelectorModelBean>> getSubDistrict(String parent);
	public ServiceResult<List<SelectorModelBean>> getPostCode(String parent);
	public ServiceResult<List<SelectorModelBean>> getPostCodeDetail(String data);
	
	public void clearBusinessUnitCache();
	public void clearPositionCache();
	public void clearParentMenuCache();
	
	/**
	 * Sale Product
	 */
	public ServiceResult<List<SelectorCustomModelBean>> getSaleProductCategory();
}
