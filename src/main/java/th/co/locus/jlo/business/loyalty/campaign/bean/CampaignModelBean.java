package th.co.locus.jlo.business.loyalty.campaign.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.BaseModelBean;

@Data
@EqualsAndHashCode(callSuper = false)
public class CampaignModelBean extends BaseModelBean {

	private Long campaignId;
	private String campaignCode;
	private String campaign;
	private String campaignType;
	private String campaignTypeId;
	private String detail;
	private String activeFlag;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
}
