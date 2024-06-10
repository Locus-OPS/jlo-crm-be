package th.co.locus.jlo.business.loyalty.manualpoint.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ManualPointCriteriaModelBean extends SortingModelBean {

	private Long txnId;
	private Long memberId;
	private String cardNumber;
	private String txnType;
	private String createStartDate;
	private String createEndDate;
	private String memberFirstName;
	private String memberLastName;
	private String memberMobile;
	private Integer buId;
}
