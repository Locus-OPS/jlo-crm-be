package th.co.locus.jlo.system.user.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserListCriteriaModelBean extends SortingModelBean {

	private String userId;
	private String firstName;
	private String lastName;
	private String status;
	private Integer buId;
	private String divId;
	private String teamId;
	private String roleCode;
	
}
