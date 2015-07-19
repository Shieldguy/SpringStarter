/**
 * 
 */
package org.springstarter.service;

import java.util.Map;

/**
 * <p>
 * @FileName    : CrudService.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 23.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public interface CrudService {
	
	public abstract Map<String, String> add(Map<String, String> mapData);
	
	public abstract Map<String, String> get(String keyId);
	
	public abstract Map<String, String> modify(String keyId, Map<String, String> mapData);
	
	public abstract Map<String, String> delete(String keyId);
	
}
