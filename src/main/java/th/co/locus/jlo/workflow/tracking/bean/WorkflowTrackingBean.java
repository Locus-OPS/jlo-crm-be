package th.co.locus.jlo.workflow.tracking.bean;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowTrackingBean extends BaseModelBean  {

	private int trackingId;

	private Long transactionId;

	private Long workflowId;

	private Long taskId;

	private Long assignmentId;

	private Integer systemId;
	
	private String eventTypeStr;
	
	private EventType eventType;

	private String statusStr;
	
	private Status status;

	private LocalDateTime timestamps;

	private String notes;
	
	private String workflowName;
	
	private String taskName;
	
	private String systemName;
	
	private Long assignedUser;
	
	private String links;
	// Enum สำหรับ EventType
    public enum EventType {
        START,
        COMPLETE,
        CANCEL,
        PENDING,
        IN_PROGRESS
    }

    // Enum สำหรับ Status
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
    
    

}
