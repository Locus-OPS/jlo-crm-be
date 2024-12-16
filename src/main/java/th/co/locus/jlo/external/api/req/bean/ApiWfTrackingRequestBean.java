package th.co.locus.jlo.external.api.req.bean;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiWfTrackingRequestBean {
	private Long trackingId;
	private String transactionId;
	private Long workflowId;
	private Long taskId;
	private Long assignmentId;
	private Long systemId;
	private String eventType;
	private String status;
	private LocalDateTime timestamps;
	private String notes;
}
