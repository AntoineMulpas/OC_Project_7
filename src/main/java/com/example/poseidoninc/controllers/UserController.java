package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.User;
import com.example.poseidoninc.repositories.UserRepository;
import com.example.poseidoninc.services.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

   private final UserAuthenticationService userAuthenticationService;

   @Autowired
    public UserController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("remoteUser","Spring Security");
        model.addAttribute("users", userAuthenticationService.findAllUsers());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user")  User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userAuthenticationService.saveAUser(user);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userAuthenticationService.findUserById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        userAuthenticationService.updateUser(id, user);

        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userAuthenticationService.deleteUserById(id);
        return "redirect:/user/list";
    }
}
