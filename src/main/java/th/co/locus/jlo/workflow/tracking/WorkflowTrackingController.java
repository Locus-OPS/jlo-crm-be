package th.co.locus.jlo.workflow.tracking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingGraphBean;

@RestController
@RequestMapping("/api/workflow-tracking")
public class WorkflowTrackingController extends BaseController {
	 	@Autowired
	 	private WorkflowTrackingService service;
	 
	 
	    @PostMapping(value="/createwftracking",produces = "application/json")
	    public ApiResponse<WorkflowTrackingBean> createTracking(@RequestBody ApiRequest<WorkflowTrackingBean> request) {
	    	try {
	    		ServiceResult<WorkflowTrackingBean> resultService=this.service.createWfTracking(request.getData());
	    		if(resultService.isSuccess()) {
	    			return ApiResponse.success(resultService.getResult());
	    		}
	    		return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
	    	}catch(Exception ex) {
	    		return ApiResponse.fail("500",ex.getMessage());
	    	}
	    }
	    
	    
	    
	    @GetMapping(value="/getallwftracking",produces = "application/json")
	    public ApiResponse<List<WorkflowTrackingBean>> getAllWftracking() {
	    	try {
	    		ServiceResult<List<WorkflowTrackingBean>> resultService=this.service.getAllWfTracking();
	    		if(resultService.isSuccess()) {
	    			return ApiResponse.success(resultService.getResult());
	    		}
	    		return ApiResponse.fail();
	    	}catch(Exception ex) {
	    		return ApiResponse.fail();
	    	}
	    }
	    
	    @PostMapping(value="/getWftrackingById",produces = "application/json")
	    public ApiResponse<List<WorkflowTrackingBean>> getWfTrackingById(@RequestBody ApiRequest<WorkflowTrackingBean> request) {
	    	try {
	    		ServiceResult<List<WorkflowTrackingBean>> resultService=this.service.getWfTrackingById(request.getData());
	    		if(resultService.isSuccess()) {
	    			return ApiResponse.success(resultService.getResult());
	    		}
	    		return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
	    	}catch(Exception ex) {
	    		return ApiResponse.fail("500",ex.getMessage());
	    	}
	    }
	    
	    @PostMapping(value="/getWftrackingByWorkflowId",produces = "application/json")
	    public ApiPageResponse<List<WorkflowTrackingBean>> getWfTrackingByWorkflowId(@RequestBody ApiPageRequest<WorkflowTrackingBean> request) {
	    	try {
	    		PageRequest pageRequest = getPageRequest(request);
	    		ServiceResult<Page<WorkflowTrackingBean>> resultService=this.service.getWfTrackingByWorkflowId(request.getData(),pageRequest);
	    		if(resultService.isSuccess()) {
	    			return ApiPageResponse.success(resultService.getResult().getContent(),resultService.getResult().getTotalElements());
	    		}
	    		return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
	    	}catch(Exception ex) {
	    		return ApiPageResponse.fail("500",ex.getMessage());
	    	}
	    }
	    
	    @PostMapping(value="/deleteWfTracking",produces = "application/json")
	    public ApiResponse<WorkflowTrackingBean> deleteWfTracking(@RequestBody ApiRequest<WorkflowTrackingBean> request) {
	    	try {
	    		ServiceResult<WorkflowTrackingBean> resultService=this.service.deleteWftrackingById(request.getData());
	    		if(resultService.isSuccess()) {
	    			return ApiResponse.success(resultService.getResult());
	    		}
	    		return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
	    	}catch(Exception ex) {
	    		return ApiResponse.fail("500",ex.getMessage());
	    	}
	    }
	    
	    @PostMapping(value="/getWftrackingGraph",produces = "application/json")
	    public ApiResponse<WorkflowTrackingGraphBean> getWftrackingGraph(@RequestBody ApiRequest<WorkflowTrackingBean> request) {
	    	try {
	    		ServiceResult<WorkflowTrackingGraphBean> serviceResult=this.service.getWftrackingGraph(request.getData());
	    		if(serviceResult.isSuccess()) {
	    			return ApiResponse.success(serviceResult.getResult());
	    		}
	    		return ApiResponse.fail(serviceResult.getResponseCode(), serviceResult.getResponseDescription());
	    	}catch(Exception ex) {
	    		return ApiResponse.fail("500",ex.getMessage());
	    	}

	    }
	    
	   
	    

}
