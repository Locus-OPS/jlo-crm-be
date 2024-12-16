package th.co.locus.jlo.external.api.resp.bean;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiWfTrackingRespBean {
	private Long trackingId;
	private String transactionId;
	private Long workflowId;
	private Long taskId;
	private Long assignmentId;
	private String assignmentName;
	private Long systemId;
	private String systemName;
	private String eventType;
	private String status;
	private LocalDateTime timestamps;
	private String notes;
}
