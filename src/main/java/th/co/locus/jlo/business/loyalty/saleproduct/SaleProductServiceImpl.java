/**
 * 
 */
package th.co.locus.jlo.business.loyalty.saleproduct;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.saleproduct.bean.SaleProductModelBean;
import th.co.locus.jlo.business.loyalty.saleproduct.bean.SearchSaleProductModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

/**
 * @author Mr.BoonOom
 *
 */
@Service
public class SaleProductServiceImpl extends BaseService implements SaleProductService {

	/**
	 * 
	 */
	public SaleProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see th.co.locus.jlo.business.loyalty.saleproduct.SaleProductService#
	 * getSaleProductList(th.co.locus.jlo.business.loyalty.saleproduct.bean.
	 * SearchSaleProductModelBean, th.co.locus.jlo.common.PageRequest)
	 */
	@Override
	public ServiceResult<Page<SaleProductModelBean>> getSaleProductList(SearchSaleProductModelBean bean,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		 return success(commonDao.selectPage("saleProduct.getSaleProductList", bean, pageRequest));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see th.co.locus.jlo.business.loyalty.saleproduct.SaleProductService#
	 * createSaleProduct(th.co.locus.jlo.business.loyalty.saleproduct.bean.
	 * SearchSaleProductModelBean)
	 */
	@Override
	public ServiceResult<SaleProductModelBean> createSaleProduct(SaleProductModelBean bean) {
	 	int result = commonDao.update("saleProduct.createSaleProduct", bean);
	 	 
        if (result > 0) {
            return success(commonDao.selectOne("saleProduct.findSaleProductById", bean));
        }
        return fail();
	}

	/*
	 * (non-Javadoc)
	 * @see th.co.locus.jlo.business.loyalty.saleproduct.SaleProductService#updateSaleProduct(th.co.locus.jlo.business.loyalty.saleproduct.bean.SaleProductModelBean)
	 */
	@Override
	public ServiceResult<SaleProductModelBean> updateSaleProduct(SaleProductModelBean bean) {
		  int result = commonDao.update("saleProduct.updateSaleProduct", bean);
	        if (result > 0) {
	            return success(commonDao.selectOne("saleProduct.findSaleProductById", bean));
	        }
	        return fail();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see th.co.locus.jlo.business.loyalty.saleproduct.SaleProductService#
	 * deleteSaleProduct(th.co.locus.jlo.business.loyalty.saleproduct.bean.
	 * SaleProductModelBean)
	 */
	@Override
	public ServiceResult<Integer> deleteSaleProduct(SaleProductModelBean bean) {
		 return success(commonDao.delete("saleProduct.deleteSaleProduct", bean));
	}

	

}
