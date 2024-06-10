package th.co.locus.jlo.business.redemption.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RedemptionMemberInfoModelBean {

	private Long memberId;
	private Long programId;
	private String programName;
	private String tierName;
	private String firstName;
	private String lastName;
	private BigDecimal currentPoint;
	
}
