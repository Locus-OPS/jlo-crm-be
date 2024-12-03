package th.co.locus.jlo.workflow.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowTaskAssignModelBean extends BaseModelBean{
	private Long assignmentId;
	private Long taskId;
	private Long userId;
	private String userName;
	private Long backupUserId;
	private String backupUserName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date assignedAt;
	private String status;
	private Long priority;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date dueDate;
	private Long sequence;
}
