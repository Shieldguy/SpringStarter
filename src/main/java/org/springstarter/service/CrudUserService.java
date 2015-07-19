/**
 * 
 */
package org.springstarter.service;

import java.util.Map;

/**
 * <p>
 * @FileName    : CrudUserService.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 23.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public interface CrudUserService extends CrudService {
	
	/**
	 * <p>
	 * Confirm user account and make account's role change to user when user request confirm
	 * </p>
	 * @param keyId email address
	 * @param confirmCode confirm code
	 * @return
	 */
	public abstract Map<String, String> confirmUser(String keyId, String confirmCode);
	
}
