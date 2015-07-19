/**
 * 
 */
package org.springstarter.controller;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.mariadb.jdbc.internal.common.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springstarter.service.CrudUserService;

/**
 * <p>
 * This controller class support user account management
 * </p>
 * @FileName    : UserController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private	CrudUserService		userService;
	
	/**
	 * <p>
	 * This method check user permission
	 * </p>
	 * @param p
	 * @param isAdmin
	 * @param isUser
	 * @return
	 */
	private int getAuthority(Principal p, boolean isAdmin, boolean isUser) {
		if(p instanceof UserDetails) {
			for(GrantedAuthority ga : ((UserDetails)p).getAuthorities()) {
				if(ga.getAuthority().compareToIgnoreCase("USER") == 0) isAdmin = true;
				if(ga.getAuthority().compareToIgnoreCase("ADMIN") == 0) isUser = true;
			}
			return 0;
		}
		return -1;
	}
	
	/**
	 * <p>
	 * This method support user registration.
	 * </p>
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public String addUser(@RequestParam Map<String, String> param, Model model) {
		//LOGGER.info("[DEBUG] addUser method called by POST");
		Map<String, String> rsltMap = null;
		try {
			rsltMap = userService.add(param);
			if(!rsltMap.containsKey("errorField")) {
				for(String key: rsltMap.keySet()) {
					model.addAttribute(key, rsltMap.get(key));
				}
				return "/welcome";
			} else {
				return "redirect:/signup?error_field=" + rsltMap.get("errorField");
			}
		} catch (Exception ex) {
			String errMsg = "Unable to store request user data information.";
			Throwable cause = ex.getCause();
		    
		    while(cause != null) {
		    	if(cause instanceof SQLIntegrityConstraintViolationException || 
		    			cause instanceof QueryException) {
		    		if(cause.getMessage().startsWith("Duplicate")) {
		    			errMsg = "Request user email was already registered.";
		    			LOGGER.warn("Request user information ({}) registered already. : {}", param.get("email"), 
 			    				cause.getMessage());
		    		} else {
		    			ex.printStackTrace();
		    			LOGGER.error("Error occurred at user adding ({}) : {}", param.get("email"), 
 			    				cause.getMessage());
		    			errMsg = "Unable to save request user data : " + cause.getMessage();
		    		}
		    		break;
		    	}
		    	cause = cause.getCause();
	    	}
			    
			return "redirect:/signup?error_field=" + errMsg; //Failed to adding request account information.";
		}
	}

	/**
	 * <p>
	 * This method support to get user account information 
	 * </p>
	 * @param email
	 * @param p
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/{email}", method=RequestMethod.GET)
	public String getUser(@PathVariable("email") String email, Principal p, Model model) {
		if(p != null) { 
			boolean isUser = false;
			boolean isAdmin = false;
			String loginUsername = p.getName();
			
			getAuthority(p, isAdmin, isUser);
			if(isAdmin)
				loginUsername = email;
LOGGER.info("[DEBUG] user info request {}", email, loginUsername);
			
			Map<String, String> rsltMap = userService.get(loginUsername);
			if(!rsltMap.containsKey("error_message")) {
				for(String key: rsltMap.keySet()) {
					model.addAttribute(key, rsltMap.get(key));
				}
			} else {
				//LOGGER.info("[DEBUG] user info request {} - {}", email, rsltMap.get("error_message"));
				model.addAttribute("error_message", rsltMap.get("error_message"));
			}
		} else {
			//LOGGER.info("[DEBUG] unauthorized user info request {}", email);
			model.addAttribute("error_message", "Unauthorized Request");
		}
		return "/userinfo";
	}

}
