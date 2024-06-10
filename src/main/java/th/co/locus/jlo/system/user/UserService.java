package th.co.locus.jlo.system.user;

import th.co.locus.jlo.common.Page;
import th.co.locus.jlo.common.PageRequest;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.system.user.bean.LoginLogCriteriaModelBean;
import th.co.locus.jlo.system.user.bean.LoginLogModelBean;
import th.co.locus.jlo.system.user.bean.UserDataModelBean;
import th.co.locus.jlo.system.user.bean.UserListCriteriaModelBean;
import th.co.locus.jlo.system.user.bean.UserLoginAttemptModelBean;
import th.co.locus.jlo.system.user.dto.UserLoginDTO;

public interface UserService {

	public ServiceResult<UserLoginDTO> getUserLoginByLoginId(String loginId);
	public void insertUserLoginAttempt(UserLoginAttemptModelBean obj);
	public ServiceResult<Page<UserDataModelBean>> getUserList(UserListCriteriaModelBean criteria, PageRequest pageRequest);
	public ServiceResult<UserDataModelBean> updateUser(UserDataModelBean user);
	public ServiceResult<UserDataModelBean> createUser(UserDataModelBean user);
	public ServiceResult<Integer> deleteUser(Long id);
	public void updateUserProfileImage(String fileName, Long id);
	public ServiceResult<Page<LoginLogModelBean>> getUserLoginLogByUserId(LoginLogCriteriaModelBean criteria, PageRequest pageRequest);
}
