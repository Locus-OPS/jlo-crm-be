package th.co.locus.jlo.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.ServiceResult;
import th.co.locus.jlo.system.menu.MenuService;
import th.co.locus.jlo.system.user.UserService;
import th.co.locus.jlo.system.user.dto.UserLoginDTO;

@Slf4j
@Service
public class SecurityUserDetailsService extends AbstractUserDetailsAuthenticationProvider implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserPasswordAuthentication userPasswrdAuthentication;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		SecurityUserDTO secUser = (SecurityUserDTO) this.loadUserByUsername(username);
		boolean check = userPasswrdAuthentication.authenticate(username, (String) authentication.getCredentials(), secUser);
		if (check) {
			return secUser;
		} else {
			throw new AuthenticationServiceException("Password verification failed.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		log.info("SecurityUserDetailsService - loadUserByUsername");
		log.info("******************************************************");
		ServiceResult<UserLoginDTO> result = userService.getUserLoginByLoginId(userId);
		SecurityUserDTO secUser;
		if (result.isSuccess()) {			
			secUser = new SecurityUserDTO(userId, result.getResult().getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_TEST")));
			secUser.setFirstName(result.getResult().getFirstName());
			secUser.setLastName(result.getResult().getLastName());
			secUser.setPictureUrl(result.getResult().getPictureUrl());
			secUser.setBuId(result.getResult().getBuId());
			secUser.setRoleCode(result.getResult().getRoleCode());
			secUser.setTokenMenuRespList(menuService.getTokenMenuListByRole(result.getResult().getRoleCode()).getResult());
			return secUser;
		} else {			
			throw new UsernameNotFoundException(result.getResponseDescription());
		}
	}

//   
//    
//    
//    private void authenPassword(String usr,String pwd,UserInfoDTO userInfo,String ipAddress) throws AuthenticationException{
//		 log.info("	Pass Input : "+pwd);
//		 log.info("	Pass Database : "+userInfo.getPassword());
//		 if(!pwd.equals(userInfo.getPassword())) {
//			 try {
//  				userManagementService.insertUserLoginAttempt(usr, "", "999" , "Password didn't match, Please try again.","Login",ipAddress);
//  	         }catch (Exception ex) {
//  				ex.printStackTrace();
//  	         }
//			 throw new AuthenticationServiceException("Password didn't match, Please try again.");
//		 }
//		 log.info(" Password Checked Pass!");
//    }
//    


//    @SuppressWarnings("unused")
//	@Override
//    protected UserDetails retrieveUser(String userId, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//    	
//    	log.info("SecUserDetailsService - retrieveUser");
//    	log.debug("************************************************************");
//    	log.debug("userId : "+userId);
//    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
//    	
//    		
//		LoginDTO loadedUser = (LoginDTO)this.loadUserByUsername(userId);
//        if(loadedUser!=null && loadedUser.getUserInfo()!=null && loadedUser.getUserInfo().getUserId()!=null) {
//        	UserInfoDTO userInfo = loadedUser.getUserInfo();
//        	if(!"Y".equalsIgnoreCase(userInfo.getUseYn())) {
//        		try {
//      				userManagementService.insertUserLoginAttempt(userId, "", "996" , "User has been disabled.","Login",TransactionUtil.getIpAddress(request));
//	  	        }catch (Exception ex) {
//	  	        	ex.printStackTrace();
//	  	        }
//            	throw new AuthenticationServiceException("User has been disabled.");
//            }
//        	
//        	
//        	if(userInfo.getLoginType().equals("1")) {
//        		try {
//        			
//        			log.info("Authentication by Active Directory");
//            		log.info("******************************************");
//    	    		HashMap<String,String> map = authenActiveDirectory(userId,authentication.getCredentials().toString(),ldapDomain,TransactionUtil.getIpAddress(request));
//    				
//    				//log.info("Sync and Role Mapping with AD - Disabled");
//    				//log.info("******************************************");
//    	    		//List<CodebookModelBean> cb = CodebookUtils.getByCodeType("ROLE_AD");
//    				//HashMap<String,String> mapRole = new HashMap<>();
//    				//for (CodebookModelBean codebookModelBean : cb) {
//    	            // 	mapRole.put(codebookModelBean.getEtc1(), codebookModelBean.getCodeId());
//    	 			//}
//    				//String role = map.get("memberOf").split(",")[0].replaceAll("CN=", "").trim();
//    	            //syncUserInfo(userId,mapRole.get(role),map);
//    	    		//userInfo.setDisplayName(map.get("displayName"));
//    	            //userInfo.setEmail(map.get("mail"));
//    	            //loadedUser.setUserInfo(userInfo);
//    				
//    				return loadedUser;
//                } catch (UsernameNotFoundException e) {
//                    throw e;
//                } catch (AuthenticationServiceException ex) {
//                    throw ex;
//                } catch (Exception e) {
//                    throw new AuthenticationServiceException(e.getMessage(), e);
//                }
//        	}else if(userInfo.getLoginType().equals("2")) {
//        		try {
//        			log.info("Basic Authentication by Password (PlainText)");
//            		log.info("******************************************");
//        			authenPassword(userId,authentication.getCredentials().toString(),userInfo,TransactionUtil.getIpAddress(request));
//    				return loadedUser;
//                } catch (AuthenticationServiceException e) {
//                    throw e;
//                } catch (Exception e) {
//                    throw new AuthenticationServiceException(e.getMessage(), e);
//                }
//        	}else if(userInfo.getLoginType().equals("3")) {
//        		try {
//        			log.info("Basic Authentication by Password (Encrypted)");
//            		log.info("******************************************");
//        			authenPassword(userId,EncryptUtil.encrypt(authentication.getCredentials().toString(),keySecret),userInfo,TransactionUtil.getIpAddress(request));
//    				return loadedUser;
//                } catch (AuthenticationServiceException e) {
//                    throw e;
//                } catch (Exception e) {
//                    throw new AuthenticationServiceException(e.getMessage(), e);
//                }
//        	}else if(userInfo.getLoginType().equals("0")) {
//        		log.info("Bypass Authentication");
//        		log.info("******************************************");
//        		return loadedUser;
//        	}else {
//        		try {
//      				userManagementService.insertUserLoginAttempt(userId, "", "997" , "Login Type is invalid. Please contact admin to set this user.","Login",TransactionUtil.getIpAddress(request));
//	  	        }catch (Exception ex) {
//	  	        	ex.printStackTrace();
//	  	        }
//        		throw new AuthenticationServiceException("Login Type is invalid. Please contact admin to set this user.");
//        	}
//        }else {
//        	try {
//  				userManagementService.insertUserLoginAttempt(userId, "", "995" , "User not found ("+userId+")","Login",TransactionUtil.getIpAddress(request));
//  	        }catch (Exception ex) {
//  	        	ex.printStackTrace();
//  	        }
//        	throw new AuthenticationServiceException("User not found.");
//        }
//        
//    }

//	@Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//    	log.info("SecUserDetailsService - loadUserByUsername");
//    	log.info("******************************************************");
//        LoginDTO userInfo;
//        ServiceResult<LoginDTO> userServiceResult = userManagementService.getLoginInfo(userId);
//        if (userServiceResult.isSuccess()) {
//            userInfo = userServiceResult.getResult();
//        } else {
//            return null;
//        }
//        log.info(userInfo.toString());
//        return userInfo;
//    }

}
