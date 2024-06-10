package th.co.locus.jlo.business.loyalty.partner;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.partner.bean.PartnerModelBean;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class PartnerServiceImpl extends BaseService implements PartnerService {

	@Override
	public ServiceResult<Page<PartnerModelBean>> getPartnerList(PartnerModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("partner.getPartnerList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<PartnerModelBean> createPartner(PartnerModelBean bean) {
		int result = commonDao.update("partner.createPartner", bean);
		if (result > 0) {
			return success(commonDao.selectOne("partner.findPartnerById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PartnerModelBean> updatePartner(PartnerModelBean bean) {
		int result = commonDao.update("partner.updatePartner", bean);
		if (result > 0) {
			return success(commonDao.selectOne("partner.findPartnerById", bean));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<Page<PartnerModelBean>> getPartnerTypeList(PartnerModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("partner.getPartnerTypeList", criteria, pageRequest));
	}	

	@Override
	public ServiceResult<PartnerModelBean> createPartnerType(PartnerModelBean bean) {
		int result = commonDao.update("partner.createPartnerType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("partner.findPartnerTypeById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<PartnerModelBean> updatePartnerType(PartnerModelBean bean) {
		int result = commonDao.update("partner.updatePartnerType", bean);
		if (result > 0) {
			return success(commonDao.selectOne("partner.findPartnerTypeById", bean));
		}
		return fail();
	}
}
