package th.co.locus.jlo.business.workflow;

import th.co.locus.jlo.business.workflow.bean.WorkflowTaskAssignModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface WorkflowTaskAssignService {
	ServiceResult<Page<WorkflowTaskAssignModelBean>> getWorkflowTaskAssignPageList(WorkflowTaskAssignModelBean bean,PageRequest pageRequest);
	ServiceResult<WorkflowTaskAssignModelBean> getWorkflowTaskAssignDetail(WorkflowTaskAssignModelBean bean);
	ServiceResult<WorkflowTaskAssignModelBean> createWorkflowTaskAssign(WorkflowTaskAssignModelBean bean);
	ServiceResult<WorkflowTaskAssignModelBean> updateWorkflowTaskAssign(WorkflowTaskAssignModelBean bean);
}
