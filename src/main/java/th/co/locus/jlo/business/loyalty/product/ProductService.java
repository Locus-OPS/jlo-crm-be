package th.co.locus.jlo.business.loyalty.product;

import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface ProductService {
	
	public ServiceResult<Page<ProductModelBean>> getProductList(SearchProductCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<ProductModelBean> createProduct(ProductModelBean bean);
	public ServiceResult<ProductModelBean> updateProduct(ProductModelBean bean);
	public ServiceResult<Integer> deleteProduct(ProductModelBean bean);
	public ServiceResult<ProductModelBean> addInventoryBalance(Long productId, Integer quantity);
	public ServiceResult<ProductModelBean> subtractInventoryBalance(Long productId, Integer quantity);
}
