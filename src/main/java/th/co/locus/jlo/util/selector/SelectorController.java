package th.co.locus.jlo.util.selector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.util.selector.bean.SelectorCustomModelBean;
import th.co.locus.jlo.util.selector.bean.SelectorModelBean;

@Api(value = "API for Selector", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/selector")
public class SelectorController extends BaseController {
	
	@Autowired
	private SelectorService selectorService;
	
	@ApiOperation(value = "Get Select List from CodeBook by CODE_TYPE")
	@PostMapping(value = "/getCodebookByCodeType", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getCodebookByCodeType(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getCodebookByCodeType(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Multiple Select List from CodeBook by CODE_TYPE")
	@PostMapping(value = "/getMultipleCodebookByCodeType", produces = "application/json")
	public ApiResponse<Map<String, List<SelectorModelBean>>> getMultipleCodebookByCodeType(@RequestBody ApiRequest<String[]> request) {
		ServiceResult<Map<String, List<SelectorModelBean>>> serviceResult = selectorService.getMultipleCodebookByCodeType(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Select List from CodeBook by CODE_TYPE and PARENT_ID")
	@PostMapping(value = "/getCodebookByCodeTypeAndParentId", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getCodebookByCodeTypeAndParentId(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getCodebookByCodeTypeAndParentId(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Business Unit List")
	@PostMapping(value = "/getBusinessUnit", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getBusinessUnit(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getBusinessUnit();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Role List")
	@PostMapping(value = "/getRole", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getRole(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getRole();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Position List")
	@PostMapping(value = "/getPosition", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPosition(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPosition();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get CodeBook Type List")
	@PostMapping(value = "/getCodebookType", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getCodebookType(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getCodebookType();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());			
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Program List")
	@PostMapping(value = "/getProgram", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getProgram(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getProgram();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}

	@ApiOperation(value = "Get Program Type List")
	@PostMapping(value = "/getShopType", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getShopType(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getShopType();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}

//	@ApiOperation(value = "Get Product Category List")
//	@PostMapping(value = "/getProductCategory", produces = "application/json")
//	public ApiResponse<List<SelectorModelBean>> getProductCategory(@RequestBody ApiRequest<String> request) {
//		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getProductCategory();
//		if (serviceResult.isSuccess()) {
//			return ApiResponse.success(serviceResult.getResult());
//		} else {
//			return ApiResponse.fail();
//		}
//	}
//	
	@ApiOperation(value = "Get Product Category List")
	@PostMapping(value = "/getProductCategory", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getProductCategory(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getProductCategory();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}
	@ApiOperation(value = "Get Sale Product Category List")
	@PostMapping(value = "/getSaleProductCategory", produces = "application/json")
	public ApiResponse<List<SelectorCustomModelBean>> getSaleProductCategory(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<SelectorCustomModelBean>> serviceResult = selectorService.getSaleProductCategory();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterCustomListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}

	@ApiOperation(value = "Get Active Campaign List")
	@PostMapping(value = "/getCampaign", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getCampaign(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getCampaign();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Attribute Group List")
	@PostMapping(value = "/getAttrGroup", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getAttrGroup(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getAttrGroup();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Attribute List")
	@PostMapping(value = "/getAttr", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getAttr(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getAttr();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Point Type List")
	@PostMapping(value = "/getPointType", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPointType(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPointType();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Partner Type List")
	@PostMapping(value = "/getPartnerType", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPartnerType(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPartnerType();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(filterListWithBuId(serviceResult.getResult()));
		} else {
			return ApiResponse.fail();
		}
	}

	@ApiOperation(value = "Get Parent Menu List")
	@PostMapping(value = "/getParentMenu", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getParentMenu(@RequestBody ApiRequest<String> request) {

		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getParentMenu();
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Province List")
	@PostMapping(value = "/getProvince", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getProvince(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getProvince(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get District List")
	@PostMapping(value = "/getDistrict", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getDistrict(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getDistrict(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Sub District List")
	@PostMapping(value = "/getSubDistrict", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getSubDistrict(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getSubDistrict(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Post Code List")
	@PostMapping(value = "/getPostCode", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPostCode(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPostCode(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@ApiOperation(value = "Get Post Code List")
	@PostMapping(value = "/getPostCodeDetail", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPostCodeDetail(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPostCodeDetail(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}

	/**
	 * Filter a result list with a current BU_ID.
	 * 
	 * @param selectorList the selector list before filer
	 * @return the filtering with the BU_ID
	 */
	private List<SelectorModelBean> filterListWithBuId(List<SelectorModelBean> selectorList) {
		return selectorList.stream().filter(selector -> getBuId() == selector.getBuId()).collect(Collectors.toList());
	}

	/**
	 * Filter a result list with a current BU_ID.
	 * 
	 * @param selectorList the selector list before filer
	 * @return the filtering with the BU_ID
	 */
	private List<SelectorCustomModelBean> filterCustomListWithBuId(List<SelectorCustomModelBean> selectorList) {
		return selectorList.stream().filter(selector -> getBuId() == selector.getBuId()).collect(Collectors.toList());
	}
}
