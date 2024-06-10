package th.co.locus.jlo.system.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import th.co.locus.jlo.common.ApiPageRequest;
import th.co.locus.jlo.common.ApiPageResponse;
import th.co.locus.jlo.common.ApiRequest;
import th.co.locus.jlo.common.ApiResponse;
import th.co.locus.jlo.common.BaseController;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;
import th.co.locus.jlo.system.position.bean.PositionCriteriaModelBean;
import th.co.locus.jlo.system.position.bean.PositionModelBean;

@Api(value = "API for Position Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/position")
public class PositionController extends BaseController {
	
	@Autowired
	private PositionService positionService;
	
	@ReadPermission
	@ApiOperation(value = "Get Position List")
	@PostMapping(value = "/getPositionList", produces = "application/json")
	public ApiPageResponse<List<PositionModelBean>> getPositionList(@RequestBody ApiPageRequest<PositionCriteriaModelBean> request) {
		StringUtil.nullifyObject(request.getData());
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
	@ApiOperation(value = "Create or Update Position")
	@PostMapping(value = "/savePosition", produces = "application/json")
	public ApiResponse<PositionModelBean> savePosition(@RequestBody ApiRequest<PositionModelBean> request) {
		ServiceResult<PositionModelBean> serviceResult;
		StringUtil.nullifyObject(request.getData());
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
	@ApiOperation(value = "Delete Position")
	@PostMapping(value = "/deletePosition", produces = "application/json")
	public ApiResponse<Integer> deletePosition(@RequestBody ApiRequest<PositionModelBean> request) {
		return ApiResponse.success(positionService.deletePosition(request.getData()).getResult());
	}

}
