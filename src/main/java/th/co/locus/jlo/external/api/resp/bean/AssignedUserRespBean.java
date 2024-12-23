package th.co.locus.jlo.external.api.resp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssignedUserRespBean {
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String roleCode;
	private String email;
}
