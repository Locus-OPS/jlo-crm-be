/**
 * 
 */
package th.co.locus.jlo.business.loyalty.saleproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.saleproduct.bean.SaleProductModelBean;
import th.co.locus.jlo.business.loyalty.saleproduct.bean.SearchSaleProductModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

/**
 * @author Mr.BoonOom
 *
 */
@Api(value = "API for Sale Product Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/saleproduct")
public class SaleProductController extends BaseController {

	/**
	 * 
	 */
	public SaleProductController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SaleProductService saleProductService;
	
	@ReadPermission
	@ApiOperation(value = "Get Sale Product List")
	@PostMapping(value = "/getSaleProductList", produces = "application/json")
	public ApiPageResponse<List<SaleProductModelBean>> getSaleProductList(@RequestBody ApiPageRequest<SearchSaleProductModelBean> request) {
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<SaleProductModelBean>> serviceResult = saleProductService.getSaleProductList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create Sale Product")
	@PostMapping(value = "/createSaleProduct", produces = "application/json")
	public ApiResponse<SaleProductModelBean> createSaleProduct(@RequestBody ApiRequest<SaleProductModelBean> request) {
		request.getData().setCreatedBy(getUserId());
		request.getData().setBuId(getBuId());
		ServiceResult<SaleProductModelBean> serviceResult;
		serviceResult = saleProductService.createSaleProduct(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Update Sale Product")
	@PostMapping(value = "/updateSaleProduct", produces = "application/json")
	public ApiResponse<SaleProductModelBean> updateSaleProduct(@RequestBody ApiRequest<SaleProductModelBean> request) {
		request.getData().setUpdatedBy(getUserId());
		ServiceResult<SaleProductModelBean> serviceResult;
		serviceResult = saleProductService.updateSaleProduct(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

	@WritePermission
	@ApiOperation(value = "Delete Sale Product")
	@PostMapping(value = "/deleteSaleProduct", produces = "application/json")
	public ApiResponse<Integer> deleteSaleProduct(@RequestBody ApiRequest<SaleProductModelBean> request) {
		return ApiResponse.success(saleProductService.deleteSaleProduct(request.getData()).getResult());
	}

}
