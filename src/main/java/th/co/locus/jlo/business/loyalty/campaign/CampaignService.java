package th.co.locus.jlo.business.loyalty.campaign;

import th.co.locus.jlo.business.loyalty.campaign.bean.CampaignModelBean;
import th.co.locus.jlo.business.loyalty.campaign.bean.SearchCampaignCriteriaModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface CampaignService {
	
	public ServiceResult<Page<CampaignModelBean>> getCampaignList(SearchCampaignCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<CampaignModelBean> createCampaign(CampaignModelBean bean);
	public ServiceResult<CampaignModelBean> updateCampaign(CampaignModelBean bean);

	
}
