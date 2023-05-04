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
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

   private final UserAuthenticationService userAuthenticationService;

    private static final Logger logger = LogManager.getLogger(UserController.class);


    @Autowired
    public UserController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @RequestMapping("/user/list")
    public String home(Model model, Authentication authentication)
    {
        model.addAttribute("remoteUser","Spring Security");
        model.addAttribute("users", userAuthenticationService.findAllUsers());
        logger.info(authentication.getName() + " has requested list of all users.");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new user.");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model, Authentication authentication) {
        if (!result.hasErrors()) {
            userAuthenticationService.saveAUser(user);
            logger.info(authentication.getName() + " has added a new user.");
            return "redirect:/user/list";
        }
        logger.error(authentication.getName() + ": User is not valid.");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        User user = userAuthenticationService.findUserById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info(authentication.getName() + " has requested page to update User");
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": User is not valid for updating id " + id);
            return "user/update";
        }

        logger.info(authentication.getName() + " has updated user with id " + id);
        userAuthenticationService.updateUser(id, user);

        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        userAuthenticationService.deleteUserById(id);
        logger.info(authentication.getName() + " has deleted user with id " + id);
        return "redirect:/user/list";
    }
}
