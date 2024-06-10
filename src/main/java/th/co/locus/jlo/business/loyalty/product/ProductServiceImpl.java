package th.co.locus.jlo.business.loyalty.product;

import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {

	@Override
	public ServiceResult<Page<ProductModelBean>> getProductList(SearchProductCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("product.getProductList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<ProductModelBean> createProduct(ProductModelBean bean) {
		int result = commonDao.update("product.createProduct", bean);
		if (result > 0) {
			return success(commonDao.selectOne("product.findProductById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProductModelBean> updateProduct(ProductModelBean bean) {
		int result = commonDao.update("product.updateProduct", bean);
		if (result > 0) {
			return success(commonDao.selectOne("product.findProductById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteProduct(ProductModelBean bean) {
		return success(commonDao.delete("product.deleteProduct", bean));
	}

	@Override
	public ServiceResult<ProductModelBean> addInventoryBalance(Long productId, Integer quantity) {
		int result = commonDao.update("product.addInventoryBalance", Map.of("productId", productId, "quantity", quantity));
		if (result > 0) {
			return success(commonDao.selectOne("product.findProductById", Map.of("productId", productId)));
		}
		return fail();
	}

	@Override
	public ServiceResult<ProductModelBean> subtractInventoryBalance(Long productId, Integer quantity) {
		int result = commonDao.update("product.subtractInventoryBalance", Map.of("productId", productId, "quantity", quantity));
		if (result > 0) {
			return success(commonDao.selectOne("product.findProductById", Map.of("productId", productId)));
		}
		return fail();
	}
}
