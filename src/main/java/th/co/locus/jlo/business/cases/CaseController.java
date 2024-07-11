/**
 * 
 */
package th.co.locus.jlo.business.cases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.business.customer.CustomerService;
import th.co.locus.jlo.business.customer.MemberService;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

/**
 * @author Mr.BoonOom
 *
 */
@RestController
@RequestMapping("api/case")
public class CaseController extends BaseController {
	
	public CaseController() {
		
	}
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MemberService memberService;
	 
	@ReadPermission
    @PostMapping(value = "/getCaseList", produces = "application/json")
    public ApiPageResponse<List<CaseModelBean>> getCaseList(@RequestBody ApiPageRequest<SearchCaseModelBean> request) {
    	request.getData().setBuId(getBuId());
    	PageRequest pageRequest = getPageRequest(request);
        ServiceResult<Page<CaseModelBean>> serviceResult = caseService.getCaseList(request.getData(), pageRequest);
        
        if (serviceResult.isSuccess()) {
            return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
        } else {
            return ApiPageResponse.fail();
        }
    }
	
	@ReadPermission
    @PostMapping(value = "/getCaseByCaseNumber", produces = "application/json")
    public ApiResponse<CaseModelBean> getCaseByCaseNumber(@RequestBody ApiPageRequest<String> request) {
    	String caseNumber = request.getData();
    	if(!caseNumber.equals(null) && !caseNumber.equals("")) {
    		ServiceResult<CaseModelBean> serviceResult = caseService.getCaseByCaseNumber(caseNumber);
    		if (serviceResult.isSuccess()) {
    			return ApiResponse.success(serviceResult.getResult());
    		}
    		return ApiResponse.fail();
    	}  	
		return ApiResponse.fail();
    }
    
	@WritePermission
    @PostMapping(value = "/createCase", produces = "application/json")
    public ApiResponse<CaseModelBean> createCase(@RequestBody ApiPageRequest<CaseModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		
		
		CaseModelBean bean = request.getData();
		bean.setCaseSlaId("1");
		bean.setBuId(getBuId());
		bean.setCreatedBy(getUserId());
		bean.setUpdatedBy(getUserId());
		
		ServiceResult<CaseModelBean> serviceResult = caseService.createCase(bean);
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	
	@WritePermission
    @PostMapping(value = "/updateCase", produces = "application/json")
    public ApiResponse<CaseModelBean> updateCase(@RequestBody ApiPageRequest<CaseModelBean> request) {
		
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		ServiceResult<CaseModelBean> serviceResult = caseService.updateCase(request.getData());
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
	
	@PostMapping(value = "/getMemberById", produces = "application/json")
	public ApiResponse<MemberData> getMemberById(@RequestBody ApiPageRequest<MemberData> request) {
		MemberData cusData = request.getData();
		cusData.setBuId(getBuId());
		ServiceResult<MemberData> serviceResult = memberService.getMemberById(cusData);
		if (serviceResult.isSuccess()) {
			MemberData customerList = serviceResult.getResult();
			return ApiResponse.success(customerList);			
		} else {
			return ApiResponse.fail();
		}
	}
	
}
