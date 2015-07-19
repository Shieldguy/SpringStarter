/**
 * 
 */
package org.springstarter.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springstarter.auth.UserAuthService;
import org.springstarter.common.StringValidator;

/**
 * <p>
 * Checking user account information with AJAX client
 * </p>
 * @FileName    : CheckAccountController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 7. 15.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@RestController
public class CheckAccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckAccountController.class);
	
	@Autowired
	private UserAuthService		userAuthService;

	/**
	 * <p>
	 * Check user account for AJAX Client.
	 * </p>
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/chkacnt", method=RequestMethod.POST)
	public String checkUserAccount(@RequestParam("username") String userName,
	        @RequestParam("password") String password, HttpServletRequest request) {
		String retStr;
		
		if(StringValidator.isHaveSQLInjection(userName) || !StringValidator.isEmail(userName) ||
				StringValidator.isHaveSQLInjection(password)) {
			return "invalid";
		}
		
		// TODO. must check brute force attack..
		// we need to check request count from same address and time duration.
		
		try {
			userAuthService.checkUserAccount(userName, password);
			retStr = "ok";
		} catch (UsernameNotFoundException | BadCredentialsException ex) {
	    	LOGGER.error("UsernameNotFoundException or BadCredentialsException occurred at username ({}) check from {} : {}", 
	    			userName, request.getRemoteAddr(), ex.getMessage());
	    	retStr = "unknown";
	    }
		
		LOGGER.info("Checked username {} from {}", userName, request.getRemoteAddr());
		
		return retStr;
	}
	
	/**
	 * <p>
	 * Check user account for AJAX Client.
	 * </p>
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/chkid", method=RequestMethod.POST)
	public String checkReistryUserAccount(@RequestParam("username") String userName,
	        @RequestParam("password") String password, HttpServletRequest request) {
		String retStr;
		
		if(StringValidator.isHaveSQLInjection(userName) || !StringValidator.isEmail(userName) ||
				StringValidator.isHaveSQLInjection(password)) {
			return "invalid";
		}
		
		// TODO. must check brute force attack..
		// we need to check request count from same address and time duration.
		
		try {
			userAuthService.checkUserAccount(userName);
			retStr = "exist";
		} catch (UsernameNotFoundException ex) {
	    	retStr = "ok";
	    }
		
		LOGGER.info("Checked username {} from {}", userName, request.getRemoteAddr());
		
		return retStr;
	}

}
