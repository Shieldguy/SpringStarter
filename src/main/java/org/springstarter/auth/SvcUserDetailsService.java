/**
 * 
 */
package org.springstarter.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springstarter.domain.Users;
import org.springstarter.repository.UsersRepository;

/**
 * <p>
 * This class called when user login, so we get user information from database and pass it to spring security.
 * </p>
 * @FileName    : SvcUserDetailsService.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Service
public class SvcUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SvcUserDetailsService.class);

	private UsersRepository usersRepository;
	
	/**
	 * @param usersRepository the usersRepository to set
	 */
	@Autowired
	public void setUsersRepository(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
LOGGER.info("[DEBUG] usersRepository ({}) was assigned at SvcUserDetailsService", usersRepository);
	}

	/**
	 * get user information from database and return it.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = usersRepository.findByUserEmailEquals(username);
		if (user == null) {
			LOGGER.warn("Unregistered user {} login requested.", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		
		return new User(username, user.getPassword(), user.isEnabled(), /* accountNonExpired */ true,
	           /* credentialsNonExpired */ true, /* accountNonLocked */ true, 
	           AuthUtil.getGrantedAuthorities(user.getRoles()));
	}
}
