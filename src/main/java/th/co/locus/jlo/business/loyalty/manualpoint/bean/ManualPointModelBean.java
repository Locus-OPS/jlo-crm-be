package th.co.locus.jlo.business.loyalty.manualpoint.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManualPointModelBean extends BaseModelBean {

	private Long txnId;
	private Long programId;
	private Long memberId;
	private String memberTierId;
	private String cardNumber;
	private String cardTier;

	private String productId;
	private String productType;
	private String productCode;
	private String productName;
	private String loyProductType;
	private String channel;
	private String txnType;
	private String txnSubType;
	private String status;

	private String pointType;
	private String pointBefore;
	private String pointAdjust;
	private String requestPoint;

	private String balancePoint;

	private String createStartDate;
	private String createEndDate;
	private String memberMobile;
	private String memberFirstName;
	private String memberLastName;
	private String memberName;

}
