package th.co.locus.jlo.business.workflow.bean;

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
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date assignedAt;
	private String status;
	private Long priority;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dueDate;
	private Long sequence;
}
