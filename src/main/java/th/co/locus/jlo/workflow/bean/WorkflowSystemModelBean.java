package th.co.locus.jlo.workflow.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowSystemModelBean extends BaseModelBean {
	private String systemId;
	private String systemName;
	private String description;
	private String isActive;
}
