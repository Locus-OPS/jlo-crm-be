package th.co.locus.jlo.business.customer.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberData {

	private Integer memberId;
	private Long customerId;
	private Boolean memberType;
	private String memberStatus;
	private String citizenId;
	private String passportNo;
	private String taxId;
	private String title;
	private String firstName;
	private String lastName;
	private String businessName;
	private String businessType;
	private String gender;
	private String nationality;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	private String occupation;
	private String maritalStatus;
	private String phoneArea;
	private String phoneNo;
	private String email;
	private String imageUrl;
	private String registrationChannel;
	private String registrationStore;
	private String remark;

	private Integer programId;
	
	private Integer buId;
	private Date approvedDate;
	private String approvedBy;
	private Date   createdDate;
	private String createdBy;
	private Date   updatedDate;
	private String updatedBy;
	
	
	private MemberCardData memberCardNoData;
	private MemberPointData memberPointData;
}
