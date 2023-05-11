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


    @GetMapping("/user/list")
    public String getListOfUsers(Model model, Authentication authentication) {
        List <User> allUsers = userAuthenticationService.findAllUsers();
        model.addAttribute("users", allUsers);
        logger.info(authentication.getName() + " requested list of all users.");
        return "/user/list";
    }

    @GetMapping("/user/add")
    public String addNewUser(Model model, Authentication authentication) {
        logger.info(authentication.getName() + " requested page to add new user.");
        return "/user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, Authentication authentication) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": User is not valid.");
            return "/user/add";
        }
        User userToSave = userAuthenticationService.saveAUser(user);
        model.addAttribute("savedBid", userToSave);
        if (result.hasErrors()) {
            model.addAttribute("error", result.getErrorCount());
        }
        logger.info(authentication.getName() + " has added a new user.");
        return "bidList/list";
    }



    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
