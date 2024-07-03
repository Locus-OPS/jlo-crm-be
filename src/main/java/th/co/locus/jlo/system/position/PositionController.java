package th.co.locus.jlo.system.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.locus.jlo.common.annotation.ReadPermission;
import th.co.locus.jlo.common.annotation.WritePermission;
import th.co.locus.jlo.common.bean.*;
import th.co.locus.jlo.common.controller.BaseController;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.position.bean.PositionCriteriaModelBean;
import th.co.locus.jlo.system.position.bean.PositionModelBean;

@RestController
@RequestMapping("api/position")
public class PositionController extends BaseController {
	
	@Autowired
	private PositionService positionService;
	
	@ReadPermission
	@PostMapping(value = "/getPositionList", produces = "application/json")
	public ApiPageResponse<List<PositionModelBean>> getPositionList(@RequestBody ApiPageRequest<PositionCriteriaModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<PositionModelBean>> serviceResult = positionService.getPositionList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@PostMapping(value = "/savePosition", produces = "application/json")
	public ApiResponse<PositionModelBean> savePosition(@RequestBody ApiRequest<PositionModelBean> request) {
		ServiceResult<PositionModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		if (request.getData().getPosId() != null) {
			serviceResult = positionService.updatePosition(request.getData());
		} else {
			serviceResult = positionService.createPosition(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/deletePosition", produces = "application/json")
	public ApiResponse<Integer> deletePosition(@RequestBody ApiRequest<PositionModelBean> request) {
		return ApiResponse.success(positionService.deletePosition(request.getData()).getResult());
	}

}
