/**
 * 
 */
package th.co.locus.jlo.business.cases.bean;

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
public class CaseModelBean extends BaseModelBean {
	
	private String caseNumber;
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
}
