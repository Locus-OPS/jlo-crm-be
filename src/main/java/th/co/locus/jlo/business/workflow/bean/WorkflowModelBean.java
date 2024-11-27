package th.co.locus.jlo.business.workflow.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowModelBean extends BaseModelBean {
	private Long workflowId;
	private String workflowName;
	private String description;
	private String status;
	private Long priority;
}
