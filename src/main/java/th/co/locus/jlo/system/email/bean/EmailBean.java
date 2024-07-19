/**
 * 
 */
package th.co.locus.jlo.system.email.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean; 

/**
 * @author apichat
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailBean extends BaseModelBean {

	private int parentId;
	private String parentModule;
	private String toEmail;
	private String ccEmail;
	private String subjectEmail;
	private String bodyEmail;
	private Long attachmentId;
	private String fromEmail;
	
	
}
