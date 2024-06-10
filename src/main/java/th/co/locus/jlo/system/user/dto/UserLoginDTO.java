package th.co.locus.jlo.system.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserLoginDTO {

	private Long id;
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
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	// AD Add on
	private String buId;
	private String buName;
	private String roleCode;
	private String roleName;
	private String posId;
	private String posName;
	private String divId;
	private String divName;

}
