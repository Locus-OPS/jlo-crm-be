/**
 * 
 */
package th.co.locus.jlo.business.cases;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.cases.bean.CaseModelBean;
import th.co.locus.jlo.business.cases.bean.SearchCaseModelBean;
import th.co.locus.jlo.business.customer.CustomerService;
import th.co.locus.jlo.business.customer.bean.CustomerData;
import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
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
@RestController
@RequestMapping("api/case")
public class CaseController extends BaseController {
	
	public CaseController() {
		
	}
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private CustomerService customerService;

	 
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
	
	
	
    @PostMapping(value = "/exportCaseReport", produces = "application/json")
    public ResponseEntity<ByteArrayResource> exportCaseReport(@RequestBody ApiRequest<String> request) {
        
        ServiceResult<ByteArrayOutputStream> serviceResult = caseService.exportCaseReport();
        if(serviceResult.isSuccess()) {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CaseReport.xlsx");
            return new ResponseEntity<>(new ByteArrayResource(serviceResult.getResult().toByteArray()),
                    header, HttpStatus.CREATED);
        }
        return ResponseEntity.noContent().build();
    }
	
	
	
}
