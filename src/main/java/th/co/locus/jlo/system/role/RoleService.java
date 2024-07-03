package th.co.locus.jlo.system.role;

import java.util.List;

import th.co.locus.jlo.common.bean.Page;
import th.co.locus.jlo.common.bean.PageRequest;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.system.role.bean.RespModelBean;
import th.co.locus.jlo.system.role.bean.RoleModelBean;
import th.co.locus.jlo.system.role.bean.UpdateRespModelBean;

public interface RoleService {
	
	public ServiceResult<Page<RoleModelBean>> getRoleList(PageRequest pageRequest);
	public ServiceResult<RoleModelBean> createRole(RoleModelBean bean);
	public ServiceResult<RoleModelBean> updateRole(RoleModelBean bean);
	public ServiceResult<Integer> deleteRole(RoleModelBean bean);
	public ServiceResult<List<RespModelBean>> getRespListByRole(String roleCode);
	public ServiceResult<Boolean> updateRespByRole(UpdateRespModelBean bean);
}
