/**
 * 
 */
package org.springstarter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springstarter.domain.Users;

/**
 * <p>
 * @FileName    : UsersRepository.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public interface UsersRepository extends CrudRepository<Users, Long> {

	/**
	 * <p>
	 * Find out a user with user email addres
	 * </p>
	 * @param Users
	 * @return
	 */
	Users findByUserEmailEquals(String userEmail);
	
	/**
	 * <p>
	 * Find out a user with user email addres and confirm code
	 * </p>
	 * @param Users
	 * @return
	 */
	Users findByUserEmailAndConfirmCodeEquals(String userEmail, String confirmCode);

}
