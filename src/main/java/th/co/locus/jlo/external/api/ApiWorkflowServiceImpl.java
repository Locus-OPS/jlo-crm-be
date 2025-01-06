package th.co.locus.jlo.external.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.external.api.req.bean.ApiWorkflowRequestBean;
import th.co.locus.jlo.external.api.resp.bean.ApiWorkflowResponseBean;
import th.co.locus.jlo.external.api.resp.bean.SystemInfoRespBean;
import th.co.locus.jlo.external.api.resp.bean.WorkflowRespBean;
import th.co.locus.jlo.external.api.resp.bean.TaskRespBean;
import th.co.locus.jlo.external.api.resp.bean.AssignmentRespBean;
import th.co.locus.jlo.workflow.bean.WorkflowTaskAssignModelBean;
import th.co.locus.jlo.external.api.resp.bean.AssignedUserRespBean;
import th.co.locus.jlo.external.api.resp.bean.RuleRespBean;

@Service
public class ApiWorkflowServiceImpl extends BaseService implements ApiWorkflowService {

	@Override
	public ServiceResult<ApiWorkflowResponseBean> getWorkflow(ApiWorkflowRequestBean bean) {
		try {
			ApiWorkflowResponseBean wfInfo=new ApiWorkflowResponseBean();
			
			//Set system info
			SystemInfoRespBean systemInfo=commonDao.selectOne("apiworkflow.getWfSystemById",bean);
			if(systemInfo!=null) {
				wfInfo.setSystem(systemInfo);
			}
			List<WorkflowRespBean> wfList = new ArrayList<>();
			//Select Workflow
			Long workflowId=null;
			if(bean.getFinanceFlg().equals("true")) {
				workflowId=commonDao.selectOne("apiworkflow.getWorkflowBySystemAmount",bean);
			}else {
				workflowId=commonDao.selectOne("apiworkflow.getWorkflowNonFinanceBySystemAmount",bean);
			}
			if(workflowId!=null) {
				List<TaskRespBean> taskList = new ArrayList<>();
				//Get Workflow
				WorkflowRespBean wf=commonDao.selectOne("apiworkflow.getWorkflowById", Map.of("workflowId",workflowId.toString()));
				List<TaskRespBean> tasks=commonDao.selectList("apiworkflow.getWorkflowTaskByWfId",Map.of("workflowId",workflowId.toString()));
				for (TaskRespBean task : tasks) {
					//set user
					List<AssignmentRespBean> assignList=new ArrayList<>();
					List<WorkflowTaskAssignModelBean> assigneeList=commonDao.selectList("workflowtaskassign.getWorkflowTaskAssignList", Map.of("taskId",task.getTaskId()));
					for (WorkflowTaskAssignModelBean assignee : assigneeList) {
						AssignmentRespBean assment=new AssignmentRespBean();
						assment.setAssignmentId(assignee.getAssignmentId());
						assment.setAssignDate(assignee.getAssignedAt());
						
						AssignedUserRespBean user=commonDao.selectOne("apiworkflow.getAssignedUser", assignee);
						if(user!=null) {
							assment.setAssignedUser(user);
						}
						
//						AssignedUserRespBean user=new AssignedUserRespBean();
//						user.setUserId(assignee.getUserId());
//						user.setUserName(assignee.getUserName());
						assignList.add(assment);
					}
					task.setAssignments(assignList);
					taskList.add(task);
				}
				Long ruleId=commonDao.selectOne("apiworkflow.getRuleId",Map.of("workflowId",workflowId,"systemId",bean.getSystemId(),"amount",bean.getAmount()));
				if(ruleId!=null) {
					RuleRespBean rule=commonDao.selectOne("apiworkflow.getWfbusinessRule", Map.of("ruleId",ruleId));
					wf.setRule(rule);
				}
				
				
				wf.setTasks(taskList);
				wfList.add(wf);
				wfInfo.setWorkflows(wfList);
				wfInfo.setMessage("Success");
				wfInfo.setStatus("200");
				return success(wfInfo);
				
			}else {
				wfInfo.setMessage("Not found workflow");
				wfInfo.setStatus("404");
				return success(wfInfo);
			}
			
		}catch(Exception ex) {
			return fail("500",ex.getMessage());
		}
		
	}

}
