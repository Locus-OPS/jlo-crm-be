package th.co.locus.jlo.business.loyalty.partner;

import th.co.locus.jlo.business.loyalty.partner.bean.PartnerModelBean;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface PartnerService {
	
	public ServiceResult<Page<PartnerModelBean>> getPartnerList(PartnerModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PartnerModelBean> createPartner(PartnerModelBean bean);
	public ServiceResult<PartnerModelBean> updatePartner(PartnerModelBean bean);

	public ServiceResult<Page<PartnerModelBean>> getPartnerTypeList(PartnerModelBean criteria, PageRequest pageRequest);
	public ServiceResult<PartnerModelBean> createPartnerType(PartnerModelBean bean);
	public ServiceResult<PartnerModelBean> updatePartnerType(PartnerModelBean bean);

	
}
