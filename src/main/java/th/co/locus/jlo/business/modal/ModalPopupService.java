package th.co.locus.jlo.business.modal;

import th.co.locus.jlo.business.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;

public interface ModalPopupService {

	public ServiceResult<Page<MemberModalBean>> searchMemberPopup(MemberModalCriteria bean, PageRequest pageRequest);
}
