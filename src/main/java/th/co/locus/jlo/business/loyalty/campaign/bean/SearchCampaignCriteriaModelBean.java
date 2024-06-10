package th.co.locus.jlo.business.loyalty.campaign.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import th.co.locus.jlo.common.SortingModelBean;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCampaignCriteriaModelBean extends SortingModelBean {

	private String campaign;
	private String campaignCode;
	private String campaignTypeId;
	private String activeFlag;
	private Integer buId;
}
