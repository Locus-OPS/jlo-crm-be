package th.co.locus.jlo.business.customer.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerListCriteria extends SortingModelBean {

	private String firstName;
	private String lastName;
	private String citizenId;
	private String passportNo;
	private String memberCardNo;
	private Integer buId;
	
}
