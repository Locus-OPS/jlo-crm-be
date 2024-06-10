package th.co.locus.jlo.business.loyalty.saleproductcategory;

import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SaleProductCategoryModelBean;
import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SearchSaleProductCategoryCriteriaModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface SaleProductCategoryService {
	
	public ServiceResult<Page<SaleProductCategoryModelBean>> getSaleProductCategoryList(SearchSaleProductCategoryCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<SaleProductCategoryModelBean> createSaleProductCategory(SaleProductCategoryModelBean bean);
	public ServiceResult<SaleProductCategoryModelBean> updateSaleProductCategory(SaleProductCategoryModelBean bean);

}
