package th.co.locus.jlo.business.loyalty.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.shop.bean.SearchShopCriteriaModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopModelBean;
import th.co.locus.jlo.business.loyalty.shop.bean.ShopTypeModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

@Api(value = "API for Loyalty Program Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/shop")
public class ShopController extends BaseController {
	
	@Autowired
	private ShopService shopService;
	
	@ApiOperation(value = "Get Shop List")
	@PostMapping(value = "/getShopList", produces = "application/json")
	public ApiPageResponse<List<ShopModelBean>> getShopList(@RequestBody ApiPageRequest<SearchShopCriteriaModelBean> request) throws Exception {

		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ShopModelBean>> serviceResult = shopService.getShopList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Create or Update Shop")
	@PostMapping(value = "/saveShop", produces = "application/json")
	public ApiResponse<ShopModelBean> saveShop(@RequestBody ApiRequest<ShopModelBean> request) {
		ServiceResult<ShopModelBean> serviceResult;
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = shopService.updateShop(request.getData());
		} else {
			serviceResult = shopService.createShop(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	
	// From here -> Program Type
	
	@ApiOperation(value = "Get Shop Type List")
	@PostMapping(value = "/getShopTypeList", produces = "application/json")
	public ApiPageResponse<List<ShopTypeModelBean>> getShopTypeList(@RequestBody ApiPageRequest<SearchShopCriteriaModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ShopTypeModelBean>> serviceResult = shopService.getShopTypeList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@ApiOperation(value = "Create or Update Shop Type")
	@PostMapping(value = "/saveShopType", produces = "application/json")
	public ApiResponse<ShopTypeModelBean> saveShopType(@RequestBody ApiRequest<ShopTypeModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		
		ServiceResult<ShopTypeModelBean> serviceResult;
		
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		
		if (request.getData().getCreatedDate() != null) {			
			serviceResult = shopService.updateShopType(request.getData());
		} else {
			serviceResult = shopService.createShopType(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}

}
