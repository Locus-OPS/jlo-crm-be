package th.co.locus.jlo.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.workflow.bean.BusinessRuleModelBean;
import th.co.locus.jlo.workflow.bean.WorkFlowTaskModelBean;
import th.co.locus.jlo.workflow.bean.WorkflowModelBean;
import th.co.locus.jlo.workflow.bean.WorkflowTaskAssignModelBean;

@Slf4j
@RestController
@RequestMapping("api/workflow")
public class WorkflowController extends BaseController {
	
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private BusinessRuleService businessRuleService;
	@Autowired
	private WorkflowTaskService workflowTaskService;
	@Autowired
	private WorkflowTaskAssignService workflowTaskAssignService;
	
	@PostMapping(value="/getWorkflowPageList",produces = "application/json")
	public ApiPageResponse<List<WorkflowModelBean>> getWorkflowPageList(@RequestBody ApiPageRequest<WorkflowModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<WorkflowModelBean>> resultService=this.workflowService.getWorkflowPageList(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			log.error(ex.getMessage());
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getWorkflowDetail",produces = "application/json")
	public ApiResponse<WorkflowModelBean> getWorkflowDetail(@RequestBody ApiRequest<WorkflowModelBean> request) {
		try {
			ServiceResult<WorkflowModelBean> resultService=this.workflowService.getWorkflowDetail(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createWorkflow",produces = "application/json")
	public ApiResponse<WorkflowModelBean> createWorkflow(@RequestBody ApiRequest<WorkflowModelBean> request) {
		try {
			request.getData().setBuId(getBuId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<WorkflowModelBean> serviceResult=this.workflowService.createWorkflow(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
			return ApiResponse.fail("500","Unable to create workflow");
		}catch(Exception ex) {
			return ApiResponse.fail("500","Unable to create workflow");
		}
	}
	
	@PostMapping(value="/updateWorkflow",produces = "application/json")
	public ApiResponse<WorkflowModelBean> updateWorkflow(@RequestBody ApiRequest<WorkflowModelBean> request) {
		try {
			request.getData().setBuId(getBuId());
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			ServiceResult<WorkflowModelBean> serviceResult=this.workflowService.updateWorkflow(request.getData());
			if(serviceResult.isSuccess()) {
				return ApiResponse.success(serviceResult.getResult());
			}
			return ApiResponse.fail("500","Unable to edit workflow");
		}catch(Exception ex) {
			return ApiResponse.fail("500","Unable to edit workflow");
		}
	}
	
	@PostMapping(value="/getBusinessRulePageList",produces = "application/json")
	public ApiPageResponse<List<BusinessRuleModelBean>> getBusinessRulePageList(@RequestBody ApiPageRequest<BusinessRuleModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<BusinessRuleModelBean>> resultService=this.businessRuleService.getBusinessRulePagelist(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createBusinessRule",produces = "application/json")
	public ApiResponse<BusinessRuleModelBean> createBusinessRule(@RequestBody ApiRequest<BusinessRuleModelBean> request) {
		try {
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());
			
			ServiceResult<BusinessRuleModelBean> resultService=this.businessRuleService.createBusinessRule(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/updateBusinessRule",produces = "application/json")
	public ApiResponse<BusinessRuleModelBean> updateBusinessRule(@RequestBody ApiRequest<BusinessRuleModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());
			ServiceResult<BusinessRuleModelBean> resultService=this.businessRuleService.updateBusinessRule(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getWorkflowTaskPageList" ,produces = "application/json")
	public ApiPageResponse<List<WorkFlowTaskModelBean>> getWorkflowTaskPageList(@RequestBody ApiPageRequest<WorkFlowTaskModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<WorkFlowTaskModelBean>> resultService=this.workflowTaskService.getWorkflowTaskPageList(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getWorkflowTaskDetail",produces = "application/json")
	public ApiResponse<WorkFlowTaskModelBean> getWorkflowTaskDetail(@RequestBody ApiRequest<WorkFlowTaskModelBean> request) {
		try {
			ServiceResult<WorkFlowTaskModelBean> resultService=this.workflowTaskService.getWorkflowTaskDetail(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createWorkflowTask",produces = "application/json")
	public ApiResponse<WorkFlowTaskModelBean> createWorkflowTask(@RequestBody ApiRequest<WorkFlowTaskModelBean> request) {
		try {
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());

			ServiceResult<WorkFlowTaskModelBean> resultService=this.workflowTaskService.createWorkflowTask(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/updateWorkflowTask",produces = "application/json")
	public ApiResponse<WorkFlowTaskModelBean> updateWorkflowTask(@RequestBody ApiRequest<WorkFlowTaskModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());
			ServiceResult<WorkFlowTaskModelBean> resultService=this.workflowTaskService.updateWorkflowTask(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getWorkflowTaskAssignPageList",produces = "application/json")
	public ApiPageResponse<List<WorkflowTaskAssignModelBean>> getWorkflowTaskAssignOageList(@RequestBody ApiPageRequest<WorkflowTaskAssignModelBean> request) {
		try {
			PageRequest pageRequest = getPageRequest(request);
			ServiceResult<Page<WorkflowTaskAssignModelBean>> resultService=this.workflowTaskAssignService.getWorkflowTaskAssignPageList(request.getData(), pageRequest);
			if(resultService.isSuccess()) {
				return ApiPageResponse.success(resultService.getResult().getContent(), resultService.getResult().getTotalElements());
			}
			return ApiPageResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiPageResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/getWorkflowTaskAssignDetail",produces = "application/json")
	public ApiResponse<WorkflowTaskAssignModelBean> getWorkflowTaskAssignDetail(@RequestBody ApiRequest<WorkflowTaskAssignModelBean> request) {
		try {
			ServiceResult<WorkflowTaskAssignModelBean> resultService=this.workflowTaskAssignService.getWorkflowTaskAssignDetail(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/createWorkflowTaskAssign",produces = "application/json")
	public ApiResponse<WorkflowTaskAssignModelBean> createWorkflowTaskAssign(@RequestBody ApiRequest<WorkflowTaskAssignModelBean> request) {
		try {
			request.getData().setCreatedBy(getUserId());
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());
			ServiceResult<WorkflowTaskAssignModelBean> resultService=this.workflowTaskAssignService.createWorkflowTaskAssign(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
	@PostMapping(value="/updateWorkflowTaskAssign" ,produces = "application/json")
	public ApiResponse<WorkflowTaskAssignModelBean> updateWorkflowTaskAssign(@RequestBody ApiRequest<WorkflowTaskAssignModelBean> request) {
		try {
			request.getData().setUpdatedBy(getUserId());
			request.getData().setBuId(getBuId());
			ServiceResult<WorkflowTaskAssignModelBean> resultService=this.workflowTaskAssignService.updateWorkflowTaskAssign(request.getData());
			if(resultService.isSuccess()) {
				return ApiResponse.success(resultService.getResult());
			}
			return ApiResponse.fail(resultService.getResponseCode(),resultService.getResponseDescription());
		}catch(Exception ex) {
			return ApiResponse.fail("500",ex.getMessage());
		}
	}
	
}
