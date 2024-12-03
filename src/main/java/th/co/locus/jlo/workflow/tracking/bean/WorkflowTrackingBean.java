package th.co.locus.jlo.workflow.tracking.bean;

import java.time.LocalDateTime;

import lombok.Data;
import th.co.locus.jlo.workflow.tracking.constant.EventType;
import th.co.locus.jlo.workflow.tracking.constant.Status;

@Data
public class WorkflowTrackingBean {

	private int trackingId;

	private Long transactionId;

	private Long workflowId;

	private Long taskId;

	private Long assignmentId;

	private Integer systemId;

	private EventType eventType;

	private Status status;

	private LocalDateTime timestamp;

	private String notes;
	
	// Enum สำหรับ EventType
    public enum EventType {
        START,
        COMPLETE,
        CANCEL,
        PENDING
    }

    // Enum สำหรับ Status
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

}
