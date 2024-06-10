package th.co.locus.jlo.config.security;

import org.springframework.security.core.AuthenticationException;

public interface Authentication {
	
	public boolean authenticate(String username, String password, SecurityUserDTO userLogin) throws AuthenticationException;

}
