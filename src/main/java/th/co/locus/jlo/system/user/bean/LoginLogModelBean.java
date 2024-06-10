package th.co.locus.jlo.system.user.bean;

import java.util.Date;

import lombok.Data;

@Data
public class LoginLogModelBean {
	
	private String userId;
	private Date attemptDate;
	private String type;
	private String ipAddress;
	private String statusCode;
	private String statusMessage;

}
