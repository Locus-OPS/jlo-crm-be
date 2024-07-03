package th.co.locus.jlo.system.user.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogCriteriaModelBean extends SortingModelBean {
	
	private String userId;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date attemptDateFrom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date attemptDateTo;
	
	private String statusCode;
	private String statusMessage;
		
}
