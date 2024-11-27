package th.co.locus.jlo.business.workflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.business.workflow.bean.WorkflowModelBean;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;

@Slf4j
@RestController
@RequestMapping("api/workflow")
public class WorkflowController extends BaseController {
	
	@Autowired
	private WorkflowService workflowService;
	
	
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
}
