package th.co.locus.jlo.integration.line.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LineMemberInfoModelBean {

	private Long memberId;
	private String tierName;
	private String firstName;
	private String lastName;
	private BigDecimal currentPoint;
	private String pointTypeName;
	private String type;
	
}
