package th.co.locus.jlo.system.user.bean;

import java.util.Date;

import lombok.Data;

@Data
public class UserLoginAttemptModelBean {

	private Long id;
	private String userId;
	private Date attemptDate;
	private String statusCode;
	private String statusMessage;
	private String type;
	private String ipAddress;
	
}
