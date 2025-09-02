package th.co.locus.jlo.business.casenotilog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogHeaderModelbean;
import th.co.locus.jlo.business.casenotilog.bean.CaseNotificationLogModelbean;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
@Log4j2
@RestController
@RequestMapping("api/casenoti")
public class CaseNotiLogController extends BaseController {

	@Autowired
	private CaseNotiLogService caseNotilogService;
	
	@PostMapping(value="/createcasenotilog",produces = "application/json")
	public ApiResponse<CaseNotificationLogModelbean> createCaseNotiLog(@RequestBody ApiRequest<CaseNotificationLogModelbean> req) {
		try {
			req.getData().setUserId(getUserId().toString());
			req.getData().setCreatedBy(getUserId());
			ServiceResult<CaseNotificationLogModelbean> serviceResult=this.caseNotilogService.createCaseNotiLog(req.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
			return ApiResponse.fail(serviceResult.getResponseCode(),serviceResult.getResponseDescription());
		}catch(Exception ex) {
			 log.error("Error while creating case notification log: {}", ex.getMessage(), ex);
			 return ApiResponse.fail("500","Error while creating case notification log");
		}
	}
	
	@PostMapping(value="/getcasenotilist",produces = "application/json")
	public ApiResponse<CaseNotificationLogHeaderModelbean> getCaseNotiList(@RequestBody ApiRequest<CaseNotificationLogHeaderModelbean> req) {
		try {
			ServiceResult<CaseNotificationLogHeaderModelbean> resultService=this.caseNotilogService.getCaseNotiLogList(getUserId().toString());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			 log.error("Error while get case noti list: {}", ex.getMessage(), ex);
			 return ApiResponse.fail("500","Error while get case noti list");
		}
	}
	
	@PostMapping(value="/updatecasenoti",produces = "application/json")
	public ApiResponse<CaseNotificationLogModelbean> updateCaseNoti(@RequestBody ApiRequest<CaseNotificationLogModelbean> req) {
		try {
			ServiceResult<CaseNotificationLogModelbean> resultService=this.caseNotilogService.updateReadStatusCaseNoti(req.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			 log.error("Error while get case noti list: {}", ex.getMessage(), ex);
			 return ApiResponse.fail("500","Error while get case noti list");
		}
	}
}
