package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Rating;
import com.example.poseidoninc.services.RatingService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RatingController {
    private final RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model, Authentication authentication)
    {
        List <Rating> allRating = ratingService.getAllRating();
        model.addAttribute("ratings", allRating);
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        logger.info(authentication.getName() + " has requested list of all ratings.");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new rating.");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model, Authentication authentication) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Rating is not valid.");
            return "rating/add";
        }
        ratingService.addNewRating(rating);
        logger.info(authentication.getName() + " has added a new rating.");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: get Rating by Id and to model then show to the form
        Rating ratingById = ratingService.getRatingById(id);
        model.addAttribute("ratings", ratingById);
        logger.info(authentication.getName() + " has requested page to update rating.");
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Rating is not valid for updating with id: " + id);
            return "rating/update";
        }
        ratingService.updateRatingById(id, rating);
        logger.info(authentication.getName() + " has updating rating with id " + id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        ratingService.deleteRatingById(id);
        logger.info(authentication.getName() + " has deleted Rating with id " + id);
        return "redirect:/rating/list";
    }
}
