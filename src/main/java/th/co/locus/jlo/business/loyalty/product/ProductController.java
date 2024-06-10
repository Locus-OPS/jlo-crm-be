package th.co.locus.jlo.business.loyalty.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.product.bean.ProductModelBean;
import th.co.locus.jlo.business.loyalty.product.bean.SearchProductCriteriaModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.WritePermission;

@Api(value = "API for Loyalty Product Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/loyalty")
public class ProductController extends BaseController {
	
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation(value = "Get Loyalty Product List")
	@PostMapping(value = "/getProductList", produces = "application/json")
	public ApiPageResponse<List<ProductModelBean>> getProductList(@RequestBody ApiPageRequest<SearchProductCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ProductModelBean>> serviceResult = productService.getProductList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Create or Update Loyalty Product")
	@PostMapping(value = "/saveProduct", produces = "application/json")
	public ApiResponse<ProductModelBean> saveProduct(@RequestBody ApiRequest<ProductModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<ProductModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = productService.updateProduct(request.getData());
		} else {
			serviceResult = productService.createProduct(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Loyalty Product")
	@PostMapping(value = "/deleteProduct", produces = "application/json")
	public ApiResponse<Integer> deleteProduct(@RequestBody ApiRequest<ProductModelBean> request) {
		return ApiResponse.success(productService.deleteProduct(request.getData()).getResult());
	}
}


