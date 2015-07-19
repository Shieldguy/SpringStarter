/**
 * 
 */
package org.springstarter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * This controller class support user sign out
 * </p>
 * @FileName    : SignoutController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class SignoutController {

	/**
	 * <p>
	 * This method return signout page
	 * </p>
	 * @return
	 */
	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String getHomepage() {
		return "signout";
	}
}
