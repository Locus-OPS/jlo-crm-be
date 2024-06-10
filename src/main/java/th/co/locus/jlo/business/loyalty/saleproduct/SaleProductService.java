/**
 * 
 */
package th.co.locus.jlo.business.loyalty.saleproduct;

import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.business.loyalty.saleproduct.bean.SaleProductModelBean;
import th.co.locus.jlo.business.loyalty.saleproduct.bean.SearchSaleProductModelBean;

/**
 * @author Mr.BoonOom
 *
 */
public interface SaleProductService {
	
	public ServiceResult<Page<SaleProductModelBean>> getSaleProductList(SearchSaleProductModelBean bean,
			PageRequest pageRequest);

	public ServiceResult<SaleProductModelBean> createSaleProduct(SaleProductModelBean bean);

	public ServiceResult<SaleProductModelBean> updateSaleProduct(SaleProductModelBean bean);

	public ServiceResult<Integer> deleteSaleProduct(SaleProductModelBean bean);
}
