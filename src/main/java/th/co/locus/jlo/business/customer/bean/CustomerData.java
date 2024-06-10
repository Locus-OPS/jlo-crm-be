package th.co.locus.jlo.business.customer.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;
import th.co.locus.jlo.common.ChangeLogBean;

@Data
@ToString
public class CustomerData {
	
	private Long customerId;
	private Boolean customerType;
	private String title;
	private String firstName;
	private String lastName;
	private String citizenId;
	private String passportNo;
	private String nationality;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	private String gender;
	private String maritalStatus;
	private String occupation;

	private String businessName;
	private String taxId;
	private String businessType;
	
	private String phoneArea;
	private String phoneNo;
	private String email;
	
	private String customerStatus;
	private String registrationChannel;
	private String registrationStore;
	
	private String remark;
	
	private Integer programId;
	
	private Date   approvedDate;
	private String approvedBy;
	private Date   createdDate;
	private String createdBy;
	private Date   updatedDate;
	private String updatedBy;
	private Integer buId;
	
	private Integer memberId;
	
	private List<AddressData> address;
	private List<ChangeLogBean> changeLog;
	
	private String memberCardNo;
	
	
	
}
