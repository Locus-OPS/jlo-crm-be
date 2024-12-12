package th.co.locus.jlo.external.api.resp.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRespBean {
	private int taskId;
	private String taskName;
	private String description;
	private String status;
	private List<AssignmentRespBean> assignments;
}
