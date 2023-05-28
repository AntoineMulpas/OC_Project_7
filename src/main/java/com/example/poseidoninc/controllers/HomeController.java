package com.example.poseidoninc.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This Class is used to get the Home page for users.
 * It has two methods depending on the authorities of the current user
 * Either USER or ADMIN
 */

@Controller
public class HomeController {

	private static final Logger logger = LogManager.getLogger(HomeController.class);

	/**
	 * This method is used to get the Home page for session with USER's authority
	 * @param model
	 * @param authentication
	 * @return an HTML page
	 */

	@RequestMapping("/")
	public String home(Model model, Authentication authentication)
	{
		logger.info(authentication.getName() + " has requested home page.");
		return "home";
	}

	/**
	 * This method is used to get the Home page for session with ADMIN's authority
	 * @param model
	 * @param authentication
	 * @return an HTML page
	 */

	@RequestMapping("/admin/home")
	public String adminHome(Model model, Authentication authentication)
	{
		boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		model.addAttribute("admin", admin);
		logger.info(authentication.getName() + " has requested home admin page.");
		return "home-admin";
	}


}
