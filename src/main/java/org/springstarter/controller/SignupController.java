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
 * This controller class support user sign up
 * </p>
 * @FileName    : SignupController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class SignupController {

	/**
	 * <p>
	 * This method return signup page
	 * </p>
	 * @return
	 */
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String getHomepage() {
		return "signup";
	}
}
