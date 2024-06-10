package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class RuleModelBean extends SortingModelBean{
	private Long ruleId;
	private String rule;
	private Long promotionId;
	private String promotion;
	private String activeFlag;
	private Long programId;
	private String program;
	private String ruleQuery;
	private String ruleJoin;
	private Long seq;
	private Long newSeq;
	
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy;
	private Integer buId;
}
