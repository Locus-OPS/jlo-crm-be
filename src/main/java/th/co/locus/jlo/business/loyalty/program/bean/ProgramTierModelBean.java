package th.co.locus.jlo.business.loyalty.program.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramTierModelBean extends SortingModelBean {

	private Long programId;
	private String activeFlag;

	private Long tierId;
	private String tierCode;
	private String tier;
	private String tierImgPath;

	private BigDecimal spending;
	private BigDecimal point;

	private String remark;
	private String baseFlag;

	private BigDecimal limitSpendingPerTime;
	private BigDecimal limitSpendingPerDay;
	private BigDecimal limitSpendingPerMonth;

	private Integer tierLevel;

	private Integer buId;

	private Date createdDate;
	private String createdBy;

	private Date updatedDate;
	private String updatedBy;
	
	/* For remove primary flag */
	private Long removePrimaryTierId;
}
