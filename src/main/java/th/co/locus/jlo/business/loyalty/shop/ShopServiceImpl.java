package th.co.locus.jlo.business.loyalty.shop;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopTypeModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class ShopServiceImpl extends BaseService implements ShopService {

	@Override
	public ServiceResult<Page<ShopModelBean>> getShopList(SearchShopCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("shop.getShopList", criteria, pageRequest));
	}

	@Override
	@Transactional
	public ServiceResult<ShopModelBean> createShop(ShopModelBean bean) {
		int result = commonDao.update("shop.createShop", bean);
		if (result > 0) {
			return success(commonDao.selectOne("shop.findShopById", bean));
		}
		return fail();
	}

	@Override
	@Transactional
	public ServiceResult<ShopModelBean> updateShop(ShopModelBean bean) {
		int result = commonDao.update("shop.updateShop", bean);
		if (result > 0) {
			return success(commonDao.selectOne("shop.findShopById", bean));
		}

		return fail();
	}

	@Override
	public ServiceResult<Page<ShopTypeModelBean>> getShopTypeList(SearchShopCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("shopType.getShopTypeList", criteria, pageRequest));
	}

	@Override
	@Transactional
	public ServiceResult<ShopTypeModelBean> createShopType(ShopTypeModelBean bean) {
		int result = commonDao.update("shopType.createShopType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("shopType.findShopTypeById", bean));
		}
		return fail();
	}

	@Override
	@Transactional
	public ServiceResult<ShopTypeModelBean> updateShopType(ShopTypeModelBean bean) {
		int result = commonDao.update("shopType.updateShopType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("shopType.findShopTypeById", bean));
		}
		return fail();
	}

}
