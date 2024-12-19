package th.co.locus.jlo.workflow;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.bean.WorkflowSystemModelBean;

public interface WorkflowSystemService  {

	ServiceResult<List<WorkflowSystemModelBean>> getWfSytemList();
	ServiceResult<Page<WorkflowSystemModelBean>> getWfsytemPageList(WorkflowSystemModelBean bean,PageRequest pageRequest);
	ServiceResult<WorkflowSystemModelBean> getWfsystemDetail(WorkflowSystemModelBean bean);
	ServiceResult<WorkflowSystemModelBean> createWfSystem(WorkflowSystemModelBean bean);
	ServiceResult<WorkflowSystemModelBean> updateWfsystem(WorkflowSystemModelBean bean);
}
