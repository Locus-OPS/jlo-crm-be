package th.co.locus.jlo.business.customer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;
/***
 * @author Apichat
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactData extends BaseModelBean {
	private int contId;
	private int custContId;
	private Long customerId;
	private String title;
	private String lastName;
	private String relationCd;
	private String phoneNo;
	private String email;
 
 
	
	
	
}
