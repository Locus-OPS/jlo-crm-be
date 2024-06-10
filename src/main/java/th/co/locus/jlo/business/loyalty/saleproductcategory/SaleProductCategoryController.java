package th.co.locus.jlo.business.loyalty.saleproductcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SaleProductCategoryModelBean;
import th.co.locus.jlo.business.loyalty.saleproductcategory.bean.SearchSaleProductCategoryCriteriaModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

@Api(value = "API for Loyalty Sale Product Category Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/loyalty")
public class SaleProductCategoryController extends BaseController {

	@Autowired
	private SaleProductCategoryService saleProductCategoryService;
	
	@ReadPermission
	@ApiOperation(value = "Get Loyalty Sale Product Category List")
	@PostMapping(value = "/getSaleProductCategoryList", produces = "application/json")
	public ApiPageResponse<List<SaleProductCategoryModelBean>> getSaleProductCategoryList(@RequestBody ApiPageRequest<SearchSaleProductCategoryCriteriaModelBean> request) {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<SaleProductCategoryModelBean>> serviceResult = saleProductCategoryService.getSaleProductCategoryList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Sale Product Category")
	@PostMapping(value = "/saveSaleProductCategory", produces = "application/json")
	public ApiResponse<SaleProductCategoryModelBean> saveProduct(@RequestBody ApiRequest<SaleProductCategoryModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		ServiceResult<SaleProductCategoryModelBean> serviceResult;
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = saleProductCategoryService.updateSaleProductCategory(request.getData());
		} else {
			serviceResult = saleProductCategoryService.createSaleProductCategory(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	
}
