package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.services.UserAuthenticationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * This Class is used to get the custom Login page of the secured App.
 */

@Controller
@RequestMapping("/app")
public class LoginController {

    private final UserAuthenticationService userAuthenticationService;

    private static final Logger logger = LogManager.getLogger(LoginController.class);


    @Autowired
    public LoginController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }



    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

}
