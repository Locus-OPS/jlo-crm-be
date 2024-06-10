package th.co.locus.jlo.business.loyalty.modal.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberModalCriteria extends SortingModelBean {

	private String memberFirstName;
	private String memberLastName;
	private String cardNumber;
	private String memberMobile;
	private Integer buId;
}
