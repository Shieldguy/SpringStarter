/**
 * 
 */
package org.springstarter.controller;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springstarter.SpringStarterApplication;
import org.springstarter.service.CrudUserService;

/**
 * <p>
 * This class control main root homepage 
 * </p>
 * @FileName    : HomeController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class RootHomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RootHomeController.class);
	
	@Autowired
	private	CrudUserService		userService;
	
	/**
	 * <p>
	 * When user access root homepage, if user already authenticated then shows user account information.
	 * But user not logged in then just show default page information.
	 * </p>
	 * @param model
	 * @param p
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getHomepage(Model model, Principal p) {
		if(p != null) {
			Map<String, String> rsltMap = userService.get(p.getName());
			if(!rsltMap.containsKey("error_message")) {
				for(String key: rsltMap.keySet()) {
					model.addAttribute(key, rsltMap.get(key));
				}
			} else {
				//LOGGER.info("[DEBUG] user info request {} - {}", email, rsltMap.get("error_message"));
				model.addAttribute("error_message", rsltMap.get("error_message"));
			}
		} else {
			model.addAttribute("username", "Guest");
		}
		
		return "index";
	}
	
}
