package th.co.locus.jlo.workflow;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.bean.WorkflowModelBean;
import th.co.locus.jlo.workflow.tracking.bean.WorkflowTrackingGraphBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;

public interface WorkflowService {
	ServiceResult<Page<WorkflowModelBean>> getWorkflowPageList(WorkflowModelBean bean,PageRequest page);
	ServiceResult<WorkflowModelBean> getWorkflowDetail(WorkflowModelBean bean);
	ServiceResult<WorkflowModelBean> createWorkflow(WorkflowModelBean bean);
	ServiceResult<WorkflowModelBean> updateWorkflow(WorkflowModelBean bean);
	ServiceResult<WorkflowTrackingGraphBean> getWorkflowGraphPreview(WorkflowModelBean bean);
}
