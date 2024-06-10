package th.co.locus.jlo.business.loyalty.modal;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalCriteria;
import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;

@Service
public class ModalPopupServiceImpl extends BaseService implements ModalPopupService {

	@Override
	public ServiceResult<Page<MemberModalBean>> searchMemberPopup(MemberModalCriteria bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("modal.searchMemberPopup", bean, pageRequest));
	}

	@Override
	public ServiceResult<Page<ShopModalBean>> searchShopPoup(ShopModalCriteria bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("modal.searchShopPopup", bean, pageRequest));
	}

}
