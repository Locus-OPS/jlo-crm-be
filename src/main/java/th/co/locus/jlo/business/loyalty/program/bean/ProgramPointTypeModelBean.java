package th.co.locus.jlo.business.loyalty.program.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramPointTypeModelBean extends SortingModelBean {

	private Long pointTypeId;
	private Long programId;

	private String pointType;
	private String useFor;
	private String costPerPoint;
	private String activeFlag;
	private String expiryBasisId;
	private String expiryBasis;
	private String period;
	private String unitPeriodId;
	private String unitPeriod;
	private String expiryMonthId;
	private String expiryMonth;
	private String expiryDay;
	private String primaryYn;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdDate;
	private String createdBy;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updatedDate;
	private String updatedBy;
	
	/* For remove primary flag */
	private Long removePrimaryPointTypeId;
}
