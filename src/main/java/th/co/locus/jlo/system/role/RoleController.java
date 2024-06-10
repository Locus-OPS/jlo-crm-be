package th.co.locus.jlo.system.role;

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
import th.co.locus.jlo.system.role.bean.RespModelBean;
import th.co.locus.jlo.system.role.bean.RoleModelBean;
import th.co.locus.jlo.system.role.bean.UpdateRespModelBean;

@Api(value = "API for Role Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@ReadPermission
	@ApiOperation(value = "Get Role List")
	@PostMapping(value = "/getRoleList", produces = "application/json")
	public ApiPageResponse<List<RoleModelBean>> getRoleList(@RequestBody ApiPageRequest<Object> request) {
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<RoleModelBean>> serviceResult = roleService.getRoleList(pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Create or Update Role")
	@PostMapping(value = "/saveRole", produces = "application/json")
	public ApiResponse<RoleModelBean> saveRole(@RequestBody ApiRequest<RoleModelBean> request) {
		RoleModelBean roleModelBean = request.getData();
		StringUtil.nullifyObject(roleModelBean);
		roleModelBean.setCreatedBy(getUserId());
		roleModelBean.setUpdatedBy(getUserId());
		roleModelBean.setBuId(getBuId());
		ServiceResult<RoleModelBean> serviceResult;
		if (request.getData().getId() != null) {
			serviceResult = roleService.updateRole(request.getData());
		} else {
			serviceResult = roleService.createRole(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete Role")
	@PostMapping(value = "/deleteRole", produces = "application/json")
	public ApiResponse<Integer> deleteRole(@RequestBody ApiRequest<RoleModelBean> request) {
		return ApiResponse.success(roleService.deleteRole(request.getData()).getResult());
	}

	@ReadPermission
	@ApiOperation(value = "Get Responsibility List by Role")
	@PostMapping(value = "/getRespListByRole", produces = "application/json")
	public ApiResponse<List<RespModelBean>> getRespListByRole(@RequestBody ApiRequest<String> request) {
		ServiceResult<List<RespModelBean>> serviceResult = roleService.getRespListByRole(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
	@WritePermission
	@ApiOperation(value = "Update Responsibility by Role")
	@PostMapping(value = "/updateRespByRole", produces = "application/json")
	public ApiResponse<Boolean> updateRespByRole(@RequestBody ApiRequest<UpdateRespModelBean> request) {
		ServiceResult<Boolean> serviceResult = roleService.updateRespByRole(request.getData());
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		} else {
			return ApiResponse.fail();
		}
	}
	
}
