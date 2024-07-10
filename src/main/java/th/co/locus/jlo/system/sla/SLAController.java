package th.co.locus.jlo.system.sla;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.ApiPageRequest;
import th.co.locus.jlo.common.bean.ApiPageResponse;
import th.co.locus.jlo.common.bean.ApiRequest;
import th.co.locus.jlo.common.bean.ApiResponse;
import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.sla.bean.SLACriteriaModelBean;
import th.co.locus.jlo.system.sla.bean.SLAModelBean;

@RestController
@RequestMapping("api/sla")
public class SLAController extends BaseController {
	
	@Autowired
	private SLAService slaService;
	
	@ReadPermission
	@PostMapping(value = "/getSlaList", produces = "application/json")
	public ApiPageResponse<List<SLAModelBean>> getSLAList(@RequestBody ApiPageRequest<SLACriteriaModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<SLAModelBean>> serviceResult = slaService.getSLAList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@PostMapping(value = "/saveSla", produces = "application/json")
	public ApiResponse<SLAModelBean> saveSLA(@RequestBody ApiRequest<SLAModelBean> request) {
		ServiceResult<SLAModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		if (request.getData().getSlaId() != null) {
			serviceResult = slaService.updateSLA(request.getData());
		} else {
			serviceResult = slaService.createSLA(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/deleteSla", produces = "application/json")
	public ApiResponse<Integer> deleteSLA(@RequestBody ApiRequest<SLAModelBean> request) {
		return ApiResponse.success(slaService.deleteSLA(request.getData()).getResult());
	}

}
