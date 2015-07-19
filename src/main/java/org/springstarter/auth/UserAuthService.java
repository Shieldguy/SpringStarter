package org.springstarter.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserAuthService {

	public abstract Authentication getAuthToken(String userName, String password) throws 
			UsernameNotFoundException, BadCredentialsException;
	
	public abstract String checkUserAccount(String userName, String password) throws 
			UsernameNotFoundException, BadCredentialsException;
	
	public abstract String checkUserAccount(String userName) throws UsernameNotFoundException;

}