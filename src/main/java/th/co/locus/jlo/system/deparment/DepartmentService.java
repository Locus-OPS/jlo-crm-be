package th.co.locus.jlo.system.deparment;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.deparment.bean.DepartmentCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamCriteriaModelBean;
import th.co.locus.jlo.system.deparment.bean.DepartmentTeamModelBean;

public interface DepartmentService {
	
	//Department
	public ServiceResult<Page<DepartmentModelBean>> getDepartmentList(DepartmentCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<DepartmentModelBean> createDepartment(DepartmentModelBean bean);
	public ServiceResult<DepartmentModelBean> updateDepartment(DepartmentModelBean bean);
	public ServiceResult<Integer> deleteDepartment(DepartmentModelBean bean);
	
	//Department Team
	public ServiceResult<Page<DepartmentTeamModelBean>> getDepartmentTeamList(DepartmentTeamCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<DepartmentTeamModelBean> createDepartmentTeam(DepartmentTeamModelBean bean);
	public ServiceResult<DepartmentTeamModelBean> updateDepartmentTeam(DepartmentTeamModelBean bean);
	public ServiceResult<Integer> deleteDepartmentTeam(DepartmentTeamModelBean bean);

}
