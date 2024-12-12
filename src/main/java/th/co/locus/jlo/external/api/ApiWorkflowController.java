package th.co.locus.jlo.external.api;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.external.api.bean.ApiServiceResponse;
import th.co.locus.jlo.external.api.req.bean.ApiWorkflowRequestBean;
import th.co.locus.jlo.external.api.resp.bean.ApiWorkflowResponseBean;
import th.co.locus.jlo.external.api.resp.bean.AssignedUserRespBean;
import th.co.locus.jlo.external.api.resp.bean.AssignmentRespBean;
import th.co.locus.jlo.external.api.resp.bean.RuleRespBean;
import th.co.locus.jlo.external.api.resp.bean.SystemInfoRespBean;
import th.co.locus.jlo.external.api.resp.bean.TaskRespBean;
import th.co.locus.jlo.external.api.resp.bean.WorkflowRespBean;

@Api
@RestController
@RequestMapping("workflow-service")
public class ApiWorkflowController extends BaseController {
	
	
	@ApiOperation(value = "API for Query เรียก Workflow ตาม System และ Rule")
	@PostMapping(value = "/v1/workflow/getWorkflowBySystem", produces = "application/json")
	public ApiServiceResponse<ApiWorkflowResponseBean> getWorkflowBySystemAndAmount(
			@RequestBody ApiWorkflowRequestBean request) throws ParseException {
		   ApiWorkflowResponseBean response = new ApiWorkflowResponseBean();

	        // Mockup data for SystemInfo
	        SystemInfoRespBean systemInfo = new SystemInfoRespBean();
	        systemInfo.setSystemId(2);
	        systemInfo.setSystemName("Finance");
	        response.setSystem(systemInfo);

	        // Mockup data for workflows
	        List<WorkflowRespBean> workflows = new ArrayList<>();

	        // Workflow 1: Review Request
	        WorkflowRespBean workflow1 = new WorkflowRespBean();
	        workflow1.setWorkflowId(1);
	        workflow1.setWorkflowName("Review Request");

	        RuleRespBean rule1 = new RuleRespBean();
	        rule1.setRuleType("greater_than");
	        rule1.setRuleValue1(1000);
	        workflow1.setRule(rule1);

	        TaskRespBean task1 = new TaskRespBean();
	        task1.setTaskId(10);
	        task1.setTaskName("Review Task");
	        task1.setDescription("Review the submitted allowance.");
	        task1.setStatus("Pending");

	        AssignmentRespBean assignment1 = new AssignmentRespBean();
	        assignment1.setAssignmentId(20);

	        AssignedUserRespBean assignedUser1 = new AssignedUserRespBean();
	        assignedUser1.setUserId(101);
	        assignedUser1.setUserName("John Doe");
	        assignment1.setAssignedUser(assignedUser1);
	        assignment1.setAssignDate(ZonedDateTime.parse("2024-12-01T10:00:00Z"));

	        List<AssignmentRespBean> assignments1 = new ArrayList<>();
	        assignments1.add(assignment1);
	        task1.setAssignments(assignments1);

	        List<TaskRespBean> tasks1 = new ArrayList<>();
	        tasks1.add(task1);
	        workflow1.setTasks(tasks1);

	        workflows.add(workflow1);

	        // Set workflows to response
	        response.setWorkflows(workflows);

	        return ApiServiceResponse.success(response);
		
	}
	
	
//	@GetMapping("/by-system-and-amount")
//    public ResponseEntity<WorkflowDTO> getWorkflowBySystemAndAmount(
//            @RequestParam Integer systemId,
//            @RequestParam BigDecimal amount) {
//        WorkflowDTO workflow = workflowService.getWorkflowBySystemAndAmount(systemId, amount);
//        return ResponseEntity.ok(workflow);
//    }
	
	/*
	 {
  "status": "success",
  "message": "Data retrieved successfully",
  "system": {
    "systemId": 2,
    "systemName": "Finance"
  },
  "workflows": [
    {
      "workflowId": 1,
      "workflowName": "Review Request",
      "rule": {
        "ruleType": "greater_than",
        "ruleValue1": 1000,
        "ruleValue2": null
      },
      "tasks": [
        {
          "taskId": 10,
          "taskName": "Review Task",
          "description": "Review the submitted allowance.",
          "status": "Pending",
          "assignments": [
            {
              "assignmentId": 20,
              "assignedUser": {
                "userId": 101,
                "userName": "John Doe"
              },
              "assignDate": "2024-12-01T10:00:00Z"
            }
          ]
        }
      ]
    },
    {
      "workflowId": 2,
      "workflowName": "Approve Payment",
      "rule": {
        "ruleType": "between",
        "ruleValue1": 500,
        "ruleValue2": 2000
      },
      "tasks": [
        {
          "taskId": 11,
          "taskName": "Approve Task",
          "description": "Approve the payment request.",
          "status": "In Progress",
          "assignments": [
            {
              "assignmentId": 21,
              "assignedUser": {
                "userId": 102,
                "userName": "Jane Smith"
              },
              "assignDate": "2024-12-01T12:00:00Z"
            }
          ]
        }
      ]
    }
  ]
}

	 */
}
