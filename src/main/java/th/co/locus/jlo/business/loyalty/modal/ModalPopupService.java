package th.co.locus.jlo.business.loyalty.modal;

import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalCriteria;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

public interface ModalPopupService {

	public ServiceResult<Page<MemberModalBean>> searchMemberPopup(MemberModalCriteria bean, PageRequest pageRequest);
	public ServiceResult<Page<ShopModalBean>> searchShopPoup(ShopModalCriteria bean, PageRequest pageRequest);
}
