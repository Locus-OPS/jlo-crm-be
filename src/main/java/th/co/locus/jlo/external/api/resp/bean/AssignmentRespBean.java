package th.co.locus.jlo.external.api.resp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class AssignmentRespBean {
	private int assignmentId;
	private AssignedUserRespBean assignedUser;
	private ZonedDateTime assignDate;
}
