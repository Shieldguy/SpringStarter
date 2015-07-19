package org.springstarter.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springstarter.service.CrudUserService;

/**
 * <p>
 * This class support user confirm services.
 * </p>
 * @FileName    : ConfirmController.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 24.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Controller
public class ConfirmController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmController.class);
	
	@Autowired
	private	CrudUserService		userService;
	
	/**
	 * <p>
	 * After user registration, we sending an email to user for confirmation.
	 * When user access confirm from email, then this method called.
	 * After confirmed, user account take regular member rights.
	 * </p>
	 * @param confirmCode
	 * @param email
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/confirm/{code}", method=RequestMethod.GET)
	public String setUserConfirm(@PathVariable("code") String confirmCode, 
			@RequestParam("username") String email, Model model) {
		Map<String, String> rsltMap = null;
		try {
			rsltMap = userService.confirmUser(email, confirmCode);
			if(!rsltMap.containsKey("error_message")) {
				for(String key: rsltMap.keySet()) {
					model.addAttribute(key, rsltMap.get(key));
				}
			} else {
				model.addAttribute("error_message", rsltMap.get("error_message"));
			}
		} catch (Exception ex) {
			model.addAttribute("error_message", "Unable to store request user data information.");
		}
		return "/confirmed";
	}

}
