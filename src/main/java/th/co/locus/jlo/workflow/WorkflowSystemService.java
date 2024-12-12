package th.co.locus.jlo.workflow;

import java.util.List;

import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.workflow.bean.WorkflowSystemModelBean;

public interface WorkflowSystemService  {

	ServiceResult<List<WorkflowSystemModelBean>> getWfSytemList();
}
