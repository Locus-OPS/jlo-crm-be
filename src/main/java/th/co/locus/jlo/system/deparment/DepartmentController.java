package th.co.locus.jlo.system.deparment;

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
import th.co.locus.jlo.system.deparment.bean.DepartmentCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamModelBean;

@RestController
@RequestMapping("api/department")
public class DepartmentController extends BaseController {

	@Autowired
	private DepartmentService departmentService;

	@ReadPermission
	@PostMapping(value = "/getDepartmentList", produces = "application/json")
	public ApiPageResponse<List<DepartmentModelBean>> getDepartmentList(
			@RequestBody ApiPageRequest<DepartmentCriteriaModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<DepartmentModelBean>> serviceResult = departmentService.getDepartmentList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@PostMapping(value = "/saveDepartment", produces = "application/json")
	public ApiResponse<DepartmentModelBean> saveDepartment(@RequestBody ApiRequest<DepartmentModelBean> request) {
		ServiceResult<DepartmentModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		if (request.getData().getId() != null) {
			serviceResult = departmentService.updateDepartment(request.getData());
		} else {
			serviceResult = departmentService.createDepartment(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/deleteDepartment", produces = "application/json")
	public ApiResponse<Integer> deleteDepartment(@RequestBody ApiRequest<DepartmentModelBean> request) {
		return ApiResponse.success(departmentService.deleteDepartment(request.getData()).getResult());
	}
	
	
	//Department Team
	@ReadPermission
	@PostMapping(value = "/getDepartmentTeamByDepartmentIdList", produces = "application/json")
	public ApiPageResponse<List<DepartmentTeamModelBean>> getDepartmentTeamByDepartmentIdList(
			@RequestBody ApiPageRequest<DepartmentTeamCriteriaModelBean> request) {
		CommonUtil.nullifyObject(request.getData());
		request.getData().setBuId(getBuId());
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<DepartmentTeamModelBean>> serviceResult = departmentService.getDepartmentTeamByDepartmentIdList(request.getData(),
				pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(),
					serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@PostMapping(value = "/saveDepartmentTeam", produces = "application/json")
	public ApiResponse<DepartmentTeamModelBean> saveDepartmentTeam(@RequestBody ApiRequest<DepartmentTeamModelBean> request) {
		ServiceResult<DepartmentTeamModelBean> serviceResult;
		CommonUtil.nullifyObject(request.getData());
		request.getData().setCreatedBy(getUserId());
		request.getData().setUpdatedBy(getUserId());
		request.getData().setBuId(getBuId());
		if (request.getData().getId() != null) {
			serviceResult = departmentService.updateDepartmentTeam(request.getData());
		} else {
			serviceResult = departmentService.createDepartmentTeam(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@PostMapping(value = "/deleteDepartmentTeam", produces = "application/json")
	public ApiResponse<Integer> deleteDepartmentTeam(@RequestBody ApiRequest<DepartmentTeamModelBean> request) {
		return ApiResponse.success(departmentService.deleteDepartmentTeam(request.getData()).getResult());
	}
	
	
}
