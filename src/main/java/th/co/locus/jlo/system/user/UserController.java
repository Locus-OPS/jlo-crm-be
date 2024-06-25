package th.co.locus.jlo.system.user;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.common.util.StringUtil;
import th.co.locus.jlo.config.security.annotation.ReadPermission;
import th.co.locus.jlo.config.security.annotation.WritePermission;
import th.co.locus.jlo.system.user.bean.LoginLogCriteriaModelBean;
import th.co.locus.jlo.system.user.bean.LoginLogModelBean;
import th.co.locus.jlo.system.user.bean.UserDataModelBean;
import th.co.locus.jlo.system.user.bean.UserListCriteriaModelBean;
import th.co.locus.jlo.util.file.FileService;

@Api(value = "API for User Management", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("api/user")
public class UserController extends BaseController {
	
	@Value("${attachment.path.profile_image}")
	private String profileImagePath;

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	 
	@ApiOperation(value = "Get User List")
	@PostMapping(value = "/getUserList", produces = "application/json")
	public ApiPageResponse<List<UserDataModelBean>> getUserList(@RequestBody ApiPageRequest<UserListCriteriaModelBean> request) {
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<UserDataModelBean>> serviceResult = userService.getUserList(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			List<UserDataModelBean> userList = serviceResult.getResult().getContent();
			return ApiPageResponse.success(userList, serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

	@WritePermission
	@ApiOperation(value = "Create or Update User")
	@PostMapping(value = "/saveUser", produces = "application/json")
	public ApiResponse<UserDataModelBean> saveUser(@RequestBody ApiRequest<UserDataModelBean> request) {
		UserDataModelBean userDataModelBean = request.getData();
		StringUtil.nullifyObject(userDataModelBean);
		userDataModelBean.setCreatedBy(getUserId());
		userDataModelBean.setUpdatedBy(getUserId());
		ServiceResult<UserDataModelBean> serviceResult;
		if (request.getData().getId() != null) {
			serviceResult = userService.updateUser(request.getData());
		} else {
			serviceResult = userService.createUser(request.getData());
		}
		if (serviceResult.isSuccess()) {
			return ApiResponse.success(serviceResult.getResult());
		}
		return ApiResponse.fail();
	}
	
	@WritePermission
	@ApiOperation(value = "Delete User")
	@PostMapping(value = "/deleteUser", produces = "application/json")
	public ApiResponse<Integer> deleteprogram(@RequestBody ApiRequest<Long> request) {
		return ApiResponse.success(userService.deleteUser(request.getData()).getResult());
	}

	@WritePermission
	@ApiOperation(value = "Upload Profile Image")
	@PostMapping(value = "/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id,
			@RequestParam("userId") String userId) {
		String message = "";
		try {
			String fileName = id + "_" + userId + CommonUtil.getFileExtension(file);
			fileService.saveFile(file, profileImagePath, fileName);
			userService.updateUserProfileImage(fileName, id);
			return ResponseEntity.status(HttpStatus.OK).body(fileName);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	@ApiOperation(value = "Get Profile Image")
	@GetMapping(value = "/profile_image/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
		Resource file = fileService.loadFile(profileImagePath + File.separator + fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@ReadPermission
	@ApiOperation(value = "Get User Login Log List")
	@PostMapping(value = "/getUserLoginLogList", produces = "application/json")
	public ApiPageResponse<List<LoginLogModelBean>> getUserLoginLogList(@RequestBody ApiPageRequest<LoginLogCriteriaModelBean> request) {
		PageRequest pageRequest = getPageRequest(request);
		ServiceResult<Page<LoginLogModelBean>> serviceResult = userService.getUserLoginLogByUserId(request.getData(), pageRequest);
		if (serviceResult.isSuccess()) {
			return ApiPageResponse.success(serviceResult.getResult().getContent(), serviceResult.getResult().getTotalElements());
		} else {
			return ApiPageResponse.fail();
		}
	}

}
