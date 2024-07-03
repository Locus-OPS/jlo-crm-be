package th.co.locus.jlo.business.modal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.business.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;

@RestController
@RequestMapping("api/modal")
public class ModalController extends BaseController {

	@Autowired
	private ModalPopupService modalPopupService;

	@PostMapping(value = "/searchMemberPopup", produces = "application/json")
	public ApiPageResponse<List<MemberModalBean>> searchMemberPopup(
			@RequestBody ApiPageRequest<MemberModalCriteria> request) {

		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());

		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<MemberModalBean>> serviceResult = modalPopupService.searchMemberPopup(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}

	}
	
}
