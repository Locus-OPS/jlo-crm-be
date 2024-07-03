package th.co.locus.jlo.business.modal.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.bean.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberModalBean extends BaseModelBean {

	private String memberId;
	private String memberFirstName;
	private String memberLastName;
	private String memberBusinessName;
	private String memberName;
	private String cardNumber;
	private String memberMobile;
	private String memberCitizenId;
	private String memberPassportNo;
	private String memberTierId;
	private String memberTierName;
	private String programId;
	private String memberType;
	private String memberTypeName;
}
