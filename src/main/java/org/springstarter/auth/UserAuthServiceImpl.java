/**
 * 
 */
package org.springstarter.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springstarter.domain.Users;
import org.springstarter.repository.UsersRepository;

/**
 * <p>
 * When user login, check user registration status and password 
 * </p>
 * @FileName    : UserAuthServiceImpl.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServiceImpl.class);
	
	private UsersRepository		usersRepository;

	/**
	 * @param usersRepository the usersRepository to set
	 */
	@Autowired
	public void setUsersRepository(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
LOGGER.info("[DEBUG] usersRepository ({}) was assigned at UserAuthServiceImpl", usersRepository);
	}

	private Users getUser(String userName) throws UsernameNotFoundException {
		Users user = usersRepository.findByUserEmailEquals(userName);
		if(user == null) {
			LOGGER.info("Unregistered user login requested - {}", userName);
			throw new UsernameNotFoundException("Username " + userName + " not found");
		}
		
		LOGGER.info("[DEBUG] User account ({}) founded.", userName);
		return user;
	}
	
	private Users getUser(String userName, String password) throws 
			UsernameNotFoundException, BadCredentialsException {
		Users user = getUser(userName);
	    
	    // compare ordinary password and encoded password from database.
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(!passwordEncoder.matches(password, user.getPassword())) { // password incorrect
			LOGGER.info("Incorrect password with login - {}", userName);
			throw new BadCredentialsException("Bad credential.");
		}
LOGGER.info("[DEBUG] User account check {}/{} and return correctly.", userName, password);
		return user;
	}

	/* (non-Javadoc)
	 * @see org.springstarter.auth.UserAuthService#getAuthToken(java.lang.String, java.lang.String)
	 */
	@Override
	public Authentication getAuthToken(String userName, String password) throws 
			UsernameNotFoundException, BadCredentialsException {
	
	    Users user = getUser(userName, password);
	    
	    // pass encoded password to the security module.
	    return new UsernamePasswordAuthenticationToken(userName, user.getPassword(), 
	    		AuthUtil.getGrantedAuthorities(user.getRoles()));
	}


	/* (non-Javadoc)
	 * @see org.springstarter.auth.UserAuthService#checkUserAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public String checkUserAccount(String userName, String password) throws 
			UsernameNotFoundException, BadCredentialsException {
		
		Users user = getUser(userName, password);
		
		return "ok";
	}
	
	/* (non-Javadoc)
	 * @see org.springstarter.auth.UserAuthService#checkUserAccount(java.lang.String)
	 */
	@Override
	public String checkUserAccount(String userName) throws UsernameNotFoundException {
		
		Users user = getUser(userName);
		
		return "ok";
	}
	
}
