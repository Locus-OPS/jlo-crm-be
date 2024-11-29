package th.co.locus.jlo.business.workflow.bean;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkFlowTaskModelBean extends BaseModelBean {
	private Long taskId;
	private String taskName;
	private Long workflowId;
	private String description;
	private String status;
	private Long priority;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date startDate;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date endDate;
	
}
