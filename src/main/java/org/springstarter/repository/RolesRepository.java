/**
 * 
 */
package org.springstarter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springstarter.domain.Roles;

/**
 * <p>
 * @FileName    : RolesRepository.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public interface RolesRepository extends JpaRepository<Roles, Long> {

	/**
	 * <p>
	 * Find out a role with role name;
	 * </p>
	 * @param roleName
	 * @return
	 */
	Roles	findByRoleNameEquals(String roleName);
	
}
