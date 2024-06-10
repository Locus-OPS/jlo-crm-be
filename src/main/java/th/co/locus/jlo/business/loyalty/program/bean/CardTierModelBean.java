package th.co.locus.jlo.business.loyalty.program.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardTierModelBean extends SortingModelBean {

	private Long programId;
	private Long tierCardTypeId;
	private Long tierId;

	private String tier;
	private String activeFlag;
	private String cardType;
	private String prefix;
	private String primaryFlag;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createdDate;
	private String createdBy;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date updatedDate;
	private String updatedBy;

	/* For remove primary flag */
	private Long removePrimaryCardTierId;
}
