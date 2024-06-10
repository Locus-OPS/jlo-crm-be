package th.co.locus.jlo.business.loyalty.program.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ProgramCriteriaModelBean extends SortingModelBean {

	private Long programId;
	private String programCode;
	private String program;
	private String promotionCalculateRuleId;
	private String programStatus;
	private String activeFlag;
	private Integer buId;
}
