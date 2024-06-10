package th.co.locus.jlo.business.loyalty.shop;

import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopTypeModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface ShopService {
	
	public ServiceResult<Page<ShopModelBean>> getShopList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ShopModelBean> createShop(ShopModelBean bean);
	public ServiceResult<ShopModelBean> updateShop(ShopModelBean bean);

	public ServiceResult<Page<ShopTypeModelBean>> getShopTypeList(SearchShopCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ShopTypeModelBean> createShopType(ShopTypeModelBean bean);
	public ServiceResult<ShopTypeModelBean> updateShopType(ShopTypeModelBean bean);

	
}
