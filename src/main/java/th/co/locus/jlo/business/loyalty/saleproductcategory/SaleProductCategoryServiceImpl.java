package th.co.locus.jlo.business.loyalty.saleproductcategory;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SaleProductCategoryModelBean;
import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SearchSaleProductCategoryCriteriaModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class SaleProductCategoryServiceImpl extends BaseService implements SaleProductCategoryService {

	@Override
	public ServiceResult<Page<SaleProductCategoryModelBean>> getSaleProductCategoryList(SearchSaleProductCategoryCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("saleproductcategory.getSaleProductCategoryList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<SaleProductCategoryModelBean> createSaleProductCategory(SaleProductCategoryModelBean bean) {
		int result = commonDao.update("saleproductcategory.createSaleProductCategory", bean);
		if (result > 0) {
			return success(commonDao.selectOne("saleproductcategory.findSaleProductCategoryById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<SaleProductCategoryModelBean> updateSaleProductCategory(SaleProductCategoryModelBean bean) {
		int result = commonDao.update("saleproductcategory.updateSaleProductCategory", bean);
		if (result > 0) {
			return success(commonDao.selectOne("saleproductcategory.findSaleProductCategoryById", bean));
		}
		return fail();
	}

}
