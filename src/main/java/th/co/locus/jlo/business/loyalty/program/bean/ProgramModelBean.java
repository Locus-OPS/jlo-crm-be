package th.co.locus.jlo.business.loyalty.program.bean;

import java.time.LocalDate;

import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@MappedTypes(LocalDate.class)
public class ProgramModelBean extends BaseModelBean {

	private Long programId;
	private String programNo;
	private String programCode;
	private String program;
	private String activeFlag;
	private String programStatus;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate startDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate endDate;

	private String promotionCalculateRuleId;
	private String promotionCalculateRule;
	private String pointExpireBasisId;
	private String pointExpireBasis;
	private String description;
	
	private String strStartDate;
	private String strEndDate;
}
