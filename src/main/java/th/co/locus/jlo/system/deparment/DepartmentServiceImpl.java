package th.co.locus.jlo.system.deparment;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.system.deparment.bean.DepartmentCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamModelBean;

@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {

	@Override
	public ServiceResult<Page<DepartmentModelBean>> getDepartmentList(DepartmentCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("department.getDepartmentList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<DepartmentModelBean> createDepartment(DepartmentModelBean bean) {
		int result = commonDao.update("department.createDepartment", bean);
		if (result > 0) {
			return success(commonDao.selectOne("department.findDepartmentById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<DepartmentModelBean> updateDepartment(DepartmentModelBean bean) {
		int result = commonDao.update("department.updateDepartment", bean);
		if (result > 0) {
			return success(commonDao.selectOne("department.findDepartmentById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteDepartment(DepartmentModelBean bean) {
		return success(commonDao.delete("department.deleteDepartment", bean));
	}

	@Override
	public ServiceResult<Page<DepartmentTeamModelBean>> getDepartmentTeamList(DepartmentTeamCriteriaModelBean criteria,
			PageRequest pageRequest) {
		return success(commonDao.selectPage("departmentTeam.getDepartmentTeamList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<DepartmentTeamModelBean> createDepartmentTeam(DepartmentTeamModelBean bean) {
		int result = commonDao.update("departmentTeam.createDepartmentTeam", bean);
		if (result > 0) {
			return success(commonDao.selectOne("departmentTeam.findDepartmentTeamById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<DepartmentTeamModelBean> updateDepartmentTeam(DepartmentTeamModelBean bean) {
		// TODO Auto-generated method stub   updateDepartmentTeam
		int result = commonDao.update("departmentTeam.updateDepartmentTeam", bean);
		if (result > 0) {
			return success(commonDao.selectOne("departmentTeam.findDepartmentTeamById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteDepartmentTeam(DepartmentTeamModelBean bean) {
		return success(commonDao.delete("departmentTeam.deleteDepartmentTeam", bean));
	}

}
