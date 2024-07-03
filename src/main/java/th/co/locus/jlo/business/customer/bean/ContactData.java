package th.co.locus.jlo.business.customer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
<<<<<<< HEAD
import th.co.locus.jlo.common.BaseModelBean;
/***
 * @author Apichat
 */
=======
import th.co.locus.jlo.common.bean.BaseModelBean;

>>>>>>> 1983551df2be9bf52648e5d289885646a8c6f137
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
