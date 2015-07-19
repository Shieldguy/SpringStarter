/**
 * 
 */
package org.springstarter.common;

/**
 * <p>
 * @FileName    : StringValidator.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 28.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
public class StringValidator {

	/**
	 * <p>
	 * Check string content has the sql injection string.
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isHaveSQLInjection(String str) {
		boolean isHave = false;
		
		if(str == null || str.length() <= 0)
			return false;
		
		// TODO. Implement code..
		
		return isHave;
	}
	
	/**
	 * <p>
	 * Check string format which is email format
	 * </p>
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		boolean isValid = true;
		
		if(str == null || str.length() <= 0)
			return false;
		
		// TODO. Implement code..
		
		return isValid;
	}

}
