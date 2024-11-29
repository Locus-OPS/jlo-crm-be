package th.co.locus.jlo.business.workflow;

import th.co.locus.jlo.business.workflow.bean.WorkFlowTaskModelBean;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface WorkflowTaskService {
	ServiceResult<Page<WorkFlowTaskModelBean>> getWorkflowTaskPageList(WorkFlowTaskModelBean bean,PageRequest pageRequest);
	ServiceResult<WorkFlowTaskModelBean> getWorkflowTaskDetail(WorkFlowTaskModelBean bean);
	ServiceResult<WorkFlowTaskModelBean> createWorkflowTask(WorkFlowTaskModelBean bean);
	ServiceResult<WorkFlowTaskModelBean> updateWorkflowTask(WorkFlowTaskModelBean bean);
}
