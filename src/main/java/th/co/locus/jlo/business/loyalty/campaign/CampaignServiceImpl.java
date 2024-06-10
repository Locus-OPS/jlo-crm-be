package th.co.locus.jlo.business.loyalty.campaign;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.campaign.bean.CampaignModelBean;
import th.co.locus.jlo.business.loyalty.campaign.bean.SearchCampaignCriteriaModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class CampaignServiceImpl extends BaseService implements CampaignService {

	@Override
	public ServiceResult<Page<CampaignModelBean>> getCampaignList(SearchCampaignCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("campaign.getCampaignList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<CampaignModelBean> createCampaign(CampaignModelBean bean) {
		int result = commonDao.update("campaign.createCampaign", bean);
		if (result > 0) {
			return success(commonDao.selectOne("campaign.findCampaignById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<CampaignModelBean> updateCampaign(CampaignModelBean bean) {
		int result = commonDao.update("campaign.updateCampaign", bean);
		if (result > 0) {
			return success(commonDao.selectOne("campaign.findCampaignById", bean));
		}
		return fail();
	}
	
}
