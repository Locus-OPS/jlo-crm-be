package th.co.locus.jlo.external.api.resp.bean;

import java.util.List;

import lombok.Data;

@Data
public class WorkflowRespBean {
	private int workflowId;
    private String workflowName;
    private RuleRespBean rule;
    private List<TaskRespBean> tasks;
}
