package th.co.locus.jlo.business.cases.casekb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.cases.casekb.bean.CaseKBModelBean;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
@Log4j2
@RestController
@RequestMapping("api/caseKB")
public class CaseKBController extends BaseController {
	@Autowired 
	private CaseKBService caseKBService;
	
	@PostMapping(value="/getRefKBList",produces = "application/json")
	public ApiPageResponse<List<CaseKBModelBean>> getRefKBList(@RequestBody ApiPageRequest<CaseKBModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<CaseKBModelBean>> serviceResult=this.caseKBService.getRefKBList(request.getData(), pageRequest);
			if(serviceResult.isSuccess()) {
				return ApiPageResponse.success(serviceResult.getResult().getContent(),serviceResult.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
		}catch(Exception ex) {
			log.error("An unexpected error occurred",ex);
			return ApiPageResponse.fail("500","An unexpected error occurred");
		}
	}
	
	@PostMapping(value="/createRefKB",produces = "application/json")
	public ApiResponse<CaseKBModelBean> createRefKB(@RequestBody ApiRequest<CaseKBModelBean> request) {
		try {
			ServiceResult<CaseKBModelBean> serviceResult=this.caseKBService.createRefKB(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
			return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
		}catch(Exception ex) {
			log.error("An unexpected error occurred",ex);
			return ApiResponse.fail("500","An unexpected error occurred");
		}
	}
	
	@PostMapping(value="/deleteRefKB",produces = "application/json")
	public ApiResponse<CaseKBModelBean> deleteRefKB(@RequestBody ApiRequest<CaseKBModelBean> request) {
		try {
			ServiceResult<CaseKBModelBean> serviceResult=this.caseKBService.deleteRefKB(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
			return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
		}catch(Exception ex) {
			log.error("An unexpected error occurred",ex);
			return ApiResponse.fail("500","An unexpected error occurred");
		}
	}
}
