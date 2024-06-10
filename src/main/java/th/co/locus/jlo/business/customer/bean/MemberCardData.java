package th.co.locus.jlo.business.customer.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberCardData {
	private String memberCardNo;
	private Integer memberId;
	private String primary;
	private String primaryYn;
	private String cardType;
	private String cardTierId;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private Date cardExpiryDate;
	private String cardStatus;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private Date cardIssueDate;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private Date cardActiveDate;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private Date cardInactiveDate;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private Date cardLastBlockDate;
	private String reIssueReason;
	private String reIssueCardNo;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	
	private String tierName;
	private Integer programId;
	
	private String cardBlockReason;
}
