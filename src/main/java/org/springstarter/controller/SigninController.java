/**
 * 
 */
package org.springstarter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springstarter.auth.UserAuthService;
import org.springstarter.common.StringValidator;

/**
 * <p>
 * This controller class support user sign in.
 * </p>
 * @FileName    : SigninController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class SigninController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SigninController.class);
	
	@Autowired
	private UserAuthService		userAuthService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * <p>
	 * When a error occurred, this method return modelandview with error message
	 * </p>
	 * @param errMsg
	 * @return
	 */
	private ModelAndView setAndGetErrorMessage(String errMsg) {
		ModelAndView mv = new ModelAndView("redirect:/signin?authentication_error=true");
    	mv.addObject("authentication_error", "true");
    	mv.addObject("error_message", errMsg);
    	return mv;
	}
	
	/**
	 * <p>
	 * This method return signin page
	 * </p>
	 * @return
	 */
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String getHomepage() {
		return "signin";
	}
	
	/**
	 * <p>
	 * Check login user account when user login.
	 * </p>
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST })
	public ModelAndView authentication(@RequestParam("username") String userName,
	        @RequestParam("password") String password, HttpServletRequest request) {
		ModelAndView mv;
		
		if(StringValidator.isHaveSQLInjection(userName) || !StringValidator.isEmail(userName) ||
				StringValidator.isHaveSQLInjection(password)) {
	    	LOGGER.error("Bad request for login - {}", userName);
	    	return setAndGetErrorMessage("Unknown user or bad credential.");
		}
		
		// TODO. must check brute force attack..
		// we need to check request count from same address and time duration.
		
	    try {
	    	
	    	Authentication authenticationToken = userAuthService.getAuthToken(userName, password);
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);
	        
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        securityContext.setAuthentication(authentication);
	
	        HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	        
	        LOGGER.info("Username {} authenticated successfully from {}", userName, request.getRemoteAddr());
	
	        mv = new ModelAndView("redirect:/");
	        
	    } catch (Exception ex) {
	    	LOGGER.error("UsernameNotFoundException occurred - {}", ex.getMessage());

	    	if(ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException)
	    		mv = setAndGetErrorMessage("Unknown user or bad credential.");
	    	else if(ex instanceof JDBCConnectionException)
	    		mv = setAndGetErrorMessage("Internal issue. Please retry later.");
	    	else
	    		mv = setAndGetErrorMessage("Authentication failed.");
	    }
	    return mv;
	}

}
