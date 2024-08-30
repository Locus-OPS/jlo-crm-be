package th.co.locus.jlo.system.role;

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
import th.co.locus.jlo.system.role.bean.RespModelBean;
import th.co.locus.jlo.system.role.bean.RoleModelBean;
import th.co.locus.jlo.system.role.bean.UpdateRespModelBean;

@RestController
@RequestMapping("api/role")
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@ReadPermission
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
	@PostMapping(value = "/saveRole", produces = "application/json")
	public ApiResponse<RoleModelBean> saveRole(@RequestBody ApiRequest<RoleModelBean> request) {
		RoleModelBean roleModelBean = request.getData();
		CommonUtil.nullifyObject(roleModelBean);
		roleModelBean.setCreatedBy(getUserId());
		roleModelBean.setUpdatedBy(getUserId());
		
		//roleModelBean.setBuId(getBuId());
		
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
	@PostMapping(value = "/deleteRole", produces = "application/json")
	public ApiResponse<Integer> deleteRole(@RequestBody ApiRequest<RoleModelBean> request) {
		return ApiResponse.success(roleService.deleteRole(request.getData()).getResult());
	}

	@ReadPermission
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
