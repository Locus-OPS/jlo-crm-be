package th.co.locus.jlo.business.loyalty.modal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.MemberModalCriteria;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalBean;
import th.co.locus.jlo.business.loyalty.modal.bean.ShopModalCriteria;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;

@Api(value = "API for Loyalty Popup modal", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/modal")
public class ModalController extends BaseController {

	@Autowired
	private ModalPopupService modalPopupService;

	@ApiOperation(value = "Search Loyalty member modal popup")
	@PostMapping(value = "/searchMemberPopup", produces = "application/json")
	public ApiPageResponse<List<MemberModalBean>> searchMemberPopup(
			@RequestBody ApiPageRequest<MemberModalCriteria> request) {

		StringUtil.nullifyObject(request.getData());
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
	
	@ApiOperation(value = "Search Loyalty shop modal popup")
	@PostMapping(value = "/searchShopPopup", produces = "application/json")
	public ApiPageResponse<List<ShopModalBean>> searchShopPopup(@RequestBody ApiPageRequest<ShopModalCriteria> request) {
	
		StringUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<ShopModalBean>> serviceResult = modalPopupService.searchShopPoup(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
		
	}
	
	
}
