package th.co.locus.jlo.workflow.tracking.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class WorkflowTrackingNodeBean {
	private String id;
	private String label;
	private String taskName;
	private String status;
	private String avatarUrl;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updatedDate;
}
