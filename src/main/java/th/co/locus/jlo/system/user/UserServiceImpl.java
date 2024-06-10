package th.co.locus.jlo.system.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import th.co.locus.jlo.common.BaseService;
import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.system.user.bean.LoginLogCriteriaModelBean;
import th.co.locus.jlo.system.user.bean.LoginLogModelBean;
import th.co.locus.jlo.system.user.bean.UserDataModelBean;
import th.co.locus.jlo.system.user.bean.UserListCriteriaModelBean;
import th.co.locus.jlo.system.user.bean.UserLoginAttemptModelBean;
import th.co.locus.jlo.system.user.dto.UserLoginDTO;

@Service
public class UserServiceImpl extends BaseService implements UserService {

	@Override
	public ServiceResult<UserLoginDTO> getUserLoginByLoginId(String loginId) {
		UserLoginDTO user = commonDao.selectOne("user.getUserLoginByLoginId", Map.of("username", loginId));
		if (user != null) {
			return success(user);			
		} else {
			return fail();
		}
	}
	
	@Override
	public void insertUserLoginAttempt(UserLoginAttemptModelBean obj) {
		try {
			commonDao.insert("user.insertLoginAttempt", obj);
			if ("200".equals(obj.getStatusCode())) {
				commonDao.update("user.updateUserSignOn", obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ServiceResult<Page<UserDataModelBean>> getUserList(UserListCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("user.getUserList", criteria, pageRequest));
	}

	@Override
	public ServiceResult<UserDataModelBean> updateUser(UserDataModelBean user) {
		int result = commonDao.update("user.updateUser", user);
		if (result > 0) {
			return success(commonDao.selectOne("user.findUserById", user));
		}
		return fail();
	}
	
	@Override
	public ServiceResult<UserDataModelBean> createUser(UserDataModelBean user) {
		int result = commonDao.update("user.createUser", user);
		if (result > 0) {
			return success(commonDao.selectOne("user.findUserById", user));
		}
		return fail();
	}

	@Override
	public ServiceResult<Integer> deleteUser(Long id) {
		return success(commonDao.delete("user.deleteUser", Map.of("id", id)));
	}

	@Override
	public void updateUserProfileImage(String fileName, Long id) {
		commonDao.update("user.updateUserProfileImage", Map.of("pictureUrl", fileName, "id", id));		
	}

	@Override
	public ServiceResult<Page<LoginLogModelBean>> getUserLoginLogByUserId(LoginLogCriteriaModelBean criteria, PageRequest pageRequest) {
		return success(commonDao.selectPage("user.getUserLoginLogByUserId", criteria, pageRequest));
	}

}
