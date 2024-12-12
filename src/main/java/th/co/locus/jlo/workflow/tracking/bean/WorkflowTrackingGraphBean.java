package th.co.locus.jlo.workflow.tracking.bean;

import java.util.List;

import lombok.Data;

@Data
public class WorkflowTrackingGraphBean {
	private List<WorkflowTrackingNodeBean> node;
	private List<WorkflowTrackingLinkBean> link;
}
