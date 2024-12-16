package th.co.locus.jlo.external.api.resp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
public class AssignmentRespBean {
	private Long assignmentId;
	private AssignedUserRespBean assignedUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date assignDate;
}
