package th.co.locus.jlo.business.customer.bean;

import lombok.Data;

@Data
public class MemberMasterData {
	
	private Long memberId;
	private String memberCardNo;
	private String firstName;
	private String lastName;
	private String tierName;
	private Integer buId;
	private Long promotionId;
	private String fullName;
	private Boolean memberType;
	private String businessName;

}
