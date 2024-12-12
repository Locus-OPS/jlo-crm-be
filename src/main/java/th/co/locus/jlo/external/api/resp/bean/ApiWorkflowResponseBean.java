package th.co.locus.jlo.external.api.resp.bean;

import java.util.List;

import lombok.Data;

@Data
public class ApiWorkflowResponseBean {
	private String status;
	private String message;
	private SystemInfoRespBean system;
	private List<WorkflowRespBean> workflows;
}
