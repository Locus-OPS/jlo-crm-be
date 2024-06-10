/**
 * 
 */
package th.co.locus.jlo.business.loyalty.casesact.bean;

import lombok.Data;
import th.co.locus.jlo.common.BaseModelBean;

/**
 * @author Mr.BoonOom
 *
 */
@Data
public class CaseActivityModelBean extends BaseModelBean {
	private String activityNumber;
	private String caseNumber;
	private String type;
	private String typeName;
	private String status;
	private String statusName;
	private String subject;
	private String detail;
	private String ownerCode;
	private String deptCode;
	private String ownerName;
}
