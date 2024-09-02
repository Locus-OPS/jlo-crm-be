/**
 * 
 */
package th.co.locus.jlo.business.sr.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * @author Mr.BoonOom
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceRequestModelBean extends BaseModelBean {
	
	private String srNumber;
	private String consultingNumber;
	private String type;
	private String subType;
	private String priority;
	private String status;
	private String channel;
	private String subject;
	private String detail;
	private String contactName;
	private Long customerId;
	private String owner;
	private String displayName;
	private String ownerDeptTeam;
	private String ownerDeptTeamName;
	private String ownerDept;
	private String ownerDeptName;
	
	private Date dueDate;
	private Date openedDate;
	private Date closedDate;
	private String typeName;
	private String subTypeName;
	private String priorityName;
	
	private String firstName;
	private String lastName;
	private String businessName;
	private String memberType;
	private String title;
	private String phoneNo;
	private String email;
	
	private String channelName;
	private String statusName;
	

	private Date openedDateDate;
	private Date closedDateDate;
	
	private String srSlaId;
	private String outSrNumber;
	
	private String divisionTypeCode;
	private String categoryTypeCode;
	private String serviceAreaCode;
	private String workNote;
	private String dslNumber;
	private String incNumber;
	private String dslStatus;
	private String informName;
	
}	
