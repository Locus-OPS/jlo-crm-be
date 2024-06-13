/**
 * 
 */
package th.co.locus.jlo.business.consulting.bean;

import java.io.Serializable;

import lombok.Data;
import th.co.locus.jlo.common.BaseModelBean;

/**
 * 
 */
@Data
public class ConsultingModelBean extends BaseModelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String consultingAction;
	private String consultingNumber;
	private String channelCd;
	private String statusCd;
	private String startDate;
	private String endDate;
	private String title;
	private String callingNumber;
	private String callObjectId;
	private String ownerId;
	private String note;
	private String consultingTypeCd;
	private String contactId; 
	private String agentState;
	private String reasonCode;
	
	private String customerId;
	private String customerName;
	private String ownerName;
	private String channelName;
	private String statusName;

}
