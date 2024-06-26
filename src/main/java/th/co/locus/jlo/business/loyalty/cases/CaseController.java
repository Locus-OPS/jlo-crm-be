/**
 * 
 */
package th.co.locus.jlo.business.loyalty.cases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.customer.CustomerService;
import th.co.locus.jlo.business.customer.MemberService;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.business.customer.bean.MemberData;
import th.co.locus.jlo.business.loyalty.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.loyalty.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;

/**
 * @author Mr.BoonOom
 *
 */
@Api(value = "API for Case Management", consumes = "application/json", produces = "application/json")
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
	@ApiOperation(value = "Get Case List")
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
    @ApiOperation(value = "Get Case")
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
	@ApiOperation(value = "Create Case List")
    @PostMapping(value = "/createCase", produces = "application/json")
    public ApiResponse<CaseModelBean> createCase(@RequestBody ApiPageRequest<CaseModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		
		ServiceResult<CaseModelBean> serviceResult = caseService.createCase(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
    }
	
	@WritePermission
	@ApiOperation(value = "Update Case List")
    @PostMapping(value = "/updateCase", produces = "application/json")
    public ApiResponse<CaseModelBean> updateCase(@RequestBody ApiPageRequest<CaseModelBean> request) {
		
		StringUtil.nullifyObject(request.getData());
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
	@ApiOperation(value = "Get Customer By Id")
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
	
	@ApiOperation(value = "Get Member By Id")
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
