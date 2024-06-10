package th.co.locus.jlo.system.user.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import th.co.locus.jlo.common.dto.BaseDTO;

@ToString
@Getter
@Setter
public class UserInfoDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -372730934158495568L;

	private String id;
	private String userId;
	private String loginType;
	private String firstName;
	private String lastName;
	private String nickname;
	private String displayName;
	private String useYn;
	private String email;
	private String lang;
	private String password;
	private String pictureUrl;
	
	//AD Add on
	private String buId;
	private String buName;
	private String roleCode;
	private String roleName;
	private String posId;
	private String posName;
	private String divId;
	private String divName;

	private Map<String,String> responsibility;
}
