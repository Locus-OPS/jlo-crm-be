package th.co.locus.jlo.util.selector;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.selector.bean.SelectorModelBean;

@RestController
@RequestMapping("api/selector")
public class SelectorController extends BaseController {
	
	@Autowired
	private SelectorService selectorService;


	@PostMapping(value = "/getProvince", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getProvince(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getProvince(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/getDistrict", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getDistrict(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getDistrict(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/getSubDistrict", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getSubDistrict(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getSubDistrict(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@PostMapping(value = "/getPostCode", produces = "application/json")
	public ApiResponse<List<SelectorModelBean>> getPostCode(@RequestBody ApiRequest<String> request) {
		
		ServiceResult<List<SelectorModelBean>> serviceResult = selectorService.getPostCode(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
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

}
