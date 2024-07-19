/**
 * 
 */
package th.co.locus.jlo.system.email.emaillog.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

/**
 * @author apichat
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailLogBean extends BaseModelBean {

	private Long id;
	private int parentId;
	private String parentModule;
	private String toEmail;
	private String ccEmail;
	private String subjectEmail;
	private String bodyEmail;
	private Long attachmentId;
	private String statusCode;
	private String statusDesc;
	private String deliverDesc;
}
