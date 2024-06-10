package th.co.locus.jlo.system.role;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.system.role.bean.RespModelBean;
import th.co.locus.jlo.system.role.bean.RoleModelBean;
import th.co.locus.jlo.system.role.bean.UpdateRespModelBean;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {

	@Override
	public ServiceResult<Page<RoleModelBean>> getRoleList(PageRequest pageRequest) {
		return success(commonDao.selectPage("role.getRoleList", null, pageRequest));
	}

	@Override
	public ServiceResult<RoleModelBean> createRole(RoleModelBean bean) {
		int result = commonDao.update("role.createRole", bean);
		if (result > 0) {
			return success(commonDao.selectOne("role.findRoleById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<RoleModelBean> updateRole(RoleModelBean bean) {
		int result = commonDao.update("role.updateRole", bean);
		if (result > 0) {
			return success(commonDao.selectOne("role.findRoleById", bean));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteRole(RoleModelBean bean) {
		return success(commonDao.delete("role.deleteRole", bean));
	}

	@Override
	public ServiceResult<List<RespModelBean>> getRespListByRole(String roleCode) {
		return success(commonDao.selectList("role.getRespListByRole", Map.of("roleCode", roleCode)));
	}

	@Override
	@Transactional
	public ServiceResult<Boolean> updateRespByRole(UpdateRespModelBean bean) {
		commonDao.delete("role.deleteRespByRole", Map.of("roleCode", bean.getRoleCode()));
		int rowCount = commonDao.update("role.updateRespByRole", Map.of("roleCode", bean.getRoleCode(), "respList", bean.getRespList(), "createdBy", CommonUtil.getUserId()));
		if (rowCount > 0) {
			return success(Boolean.TRUE);
		} else {
			return fail();
		}
	}


}
