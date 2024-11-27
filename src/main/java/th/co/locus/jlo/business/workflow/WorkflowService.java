package th.co.locus.jlo.business.workflow;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.business.workflow.bean.WorkflowModelBean;

public interface WorkflowService {
	ServiceResult<Page<WorkflowModelBean>> getWorkflowPageList(WorkflowModelBean bean,PageRequest page);
	ServiceResult<WorkflowModelBean> getWorkflowDetail(WorkflowModelBean bean);
	ServiceResult<WorkflowModelBean> createWorkflow(WorkflowModelBean bean);
	ServiceResult<WorkflowModelBean> updateWorkflow(WorkflowModelBean bean);
}
