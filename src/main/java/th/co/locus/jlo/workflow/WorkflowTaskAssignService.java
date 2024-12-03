package th.co.locus.jlo.workflow;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.bean.WorkflowTaskAssignModelBean;

public interface WorkflowTaskAssignService {
	ServiceResult<Page<WorkflowTaskAssignModelBean>> getWorkflowTaskAssignPageList(WorkflowTaskAssignModelBean bean,PageRequest pageRequest);
	ServiceResult<WorkflowTaskAssignModelBean> getWorkflowTaskAssignDetail(WorkflowTaskAssignModelBean bean);
	ServiceResult<WorkflowTaskAssignModelBean> createWorkflowTaskAssign(WorkflowTaskAssignModelBean bean);
	ServiceResult<WorkflowTaskAssignModelBean> updateWorkflowTaskAssign(WorkflowTaskAssignModelBean bean);
}
