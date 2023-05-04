package com.example.poseidoninc.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model, Authentication authentication)
	{
		boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		model.addAttribute("admin", admin);
		return "home-admin";
	}


}
