package th.co.locus.jlo.business.customer.bean;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberPointData {
	private Integer memberId;
	private Integer programId;
	private String programName;
	private Integer currentPoint;
	private Integer pointExpireThisYear;
	private Date lastCalculatedDate;
}
