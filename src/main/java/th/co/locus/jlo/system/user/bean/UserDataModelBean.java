package th.co.locus.jlo.system.user.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDataModelBean extends BaseModelBean {
	
	private Long id;
	private String userId;
	private String loginType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String displayName;
	private String useYn;
	private String email;
	private String password;
	private String pictureUrl;
	private String roleCode;
	private String posId;
	private String divId;
	
	private String buName;
	private String roleName;
	private String posName;
	private String divName;
	
}
