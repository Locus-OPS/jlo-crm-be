package th.co.locus.jlo.system.role.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleModelBean extends BaseModelBean {

	private Long id;
	private String roleCode;
	private String roleName;
	private String useYn;
	
}
