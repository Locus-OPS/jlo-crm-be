package th.co.locus.jlo.business.modal;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.business.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;

@Service
public class ModalPopupServiceImpl extends BaseService implements ModalPopupService {

	@Override
	public ServiceResult<Page<MemberModalBean>> searchMemberPopup(MemberModalCriteria bean, PageRequest pageRequest) {
		return success(commonDao.selectPage("modal.searchMemberPopup", bean, pageRequest));
	}

}
