/**
 * 
 */
package th.co.locus.jlo.business.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.customer.CustomerService;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.sr.bean.SearchSrModelBean;
import th.co.locus.jlo.business.sr.bean.ServiceRequestModelBean;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

/**
 * @author Mr.BoonOom
 *
 */
@Slf4j
@RestController
@RequestMapping("api/sr")
public class ServiceRequestController extends BaseController {
	
	public ServiceRequestController() {
		
	}
	
	@Autowired
	private ServiceRequestService srService;
	@Autowired
	private CustomerService customerService;

	 
	@ReadPermission
    @PostMapping(value = "/getSrList", produces = "application/json")
    public ApiPageResponse<List<ServiceRequestModelBean>> getSrList(@RequestBody ApiPageRequest<SearchSrModelBean> request) {
    	request.getData().setBuId(getBuId());
    	PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<ServiceRequestModelBean>> serviceResult = srService.getSrList(request.getData(), pageRequest);
        
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }
	
	@ReadPermission
    @PostMapping(value = "/getSrBySrNumber", produces = "application/json")
    public ApiResponse<ServiceRequestModelBean> getSrBySrNumber(@RequestBody ApiPageRequest<String> request) {
    	String srNumber = request.getData();
    	if(!srNumber.equals(null) && !srNumber.equals("")) {
    		ServiceResult<ServiceRequestModelBean> serviceResult = srService.getSrBySrNumber(srNumber);
    		if (serviceResult.isSuccess()) {
    			return ApiResponse.success(serviceResult.getResult());
    		}
    		return ApiResponse.fail();
    	}  	
		return ApiResponse.fail();
    }
    
	@WritePermission
    @PostMapping(value = "/createSr", produces = "application/json")
    public ApiResponse<ServiceRequestModelBean> createSr(@RequestBody ApiPageRequest<ServiceRequestModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		
		
		ServiceRequestModelBean bean = request.getData();
		bean.setSrSlaId("1");
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		
		log.debug("createSr> "+bean);
		log.info("createSr> "+bean);
		
		ServiceResult<ServiceRequestModelBean> serviceResult = srService.createSr(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	
	@WritePermission
    @PostMapping(value = "/updateSr", produces = "application/json")
    public ApiResponse<ServiceRequestModelBean> updateSr(@RequestBody ApiPageRequest<ServiceRequestModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		ServiceResult<ServiceRequestModelBean> serviceResult = srService.updateSr(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	
	@ReadPermission
	@PostMapping(value = "/getCustomerById", produces = "application/json")
	public ApiResponse<CustomerData> getCustomerById(@RequestBody ApiPageRequest<CustomerData> request) {
		CustomerData cusData = request.getData();
		cusData.setBuId(getBuId());
		ServiceResult<CustomerData> serviceResult = customerService.getCustomerById(cusData);
		if (serviceResult.isSuccess()) {
			CustomerData customerList = serviceResult.getResult();
			return ApiResponse.success(customerList);			
		} else {
			return ApiResponse.fail();
		}
	}
	
	
	
}
