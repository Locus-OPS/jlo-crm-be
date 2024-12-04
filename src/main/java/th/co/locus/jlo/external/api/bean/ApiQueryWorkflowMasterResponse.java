package th.co.locus.jlo.external.api.bean;

import java.util.List;

import lombok.Data;
import th.co.locus.jlo.workflow.bean.WorkflowModelBean;

@Data
public class ApiQueryWorkflowMasterResponse {
	
	private List<WorkflowModelBean> workflowList;
}
