/**
 * 
 */
package org.springstarter.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springstarter.domain.Roles;

/**
 * <p>
 * @FileName    : AuthUtil.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 23.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public class AuthUtil {

	/**
	 * <p>
	 * Return Roles list to GrandtedAuthority collection.
	 * </p>
	 * @param roles
	 * @return collection of GrantedAuthority
	 */
	public static Collection<GrantedAuthority> getGrantedAuthorities(List<Roles> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Roles role: roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

}
