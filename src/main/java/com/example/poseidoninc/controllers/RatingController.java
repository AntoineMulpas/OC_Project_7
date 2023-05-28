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

/**
 * This class is a controller for the Rating object.
 * It is annotated with the @Controller annotation to be used with Thymeleaf
 */

@Controller
public class RatingController {
    private final RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    /**
     * This method is used to get a list of all Ratings
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Ratings
     */

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

    /**
     * This method is used to get a page containing the form to add a new Rating.
     * @param rating
     * @param authentication
     * @return an HTML page containing the form to add a new rating
     */

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new rating.");
        return "rating/add";
    }

    /**
     * This method is used to validate the content of the submitted form to add a new Rating.
     * If the content is valid, then a new Rating is added and the page redirects the user
     * to the page containing all ratings.
     * @param rating
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Ratings in a case of success, otherwise the form to add a new Rating.
     */

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

    /**
     * This method is used to get the form to update a new Rating. The fields inside the form are filled
     * with the values already existing.
     * @param id
     * @param model
     * @param authentication
     * @return an HTML page containing the form to update a Rating.
     */

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: get Rating by Id and to model then show to the form
        Rating ratingById = ratingService.getRatingById(id);
        model.addAttribute("ratings", ratingById);
        logger.info(authentication.getName() + " has requested page to update rating.");
        return "rating/update";
    }

    /**
     * This method is used to validate the content of the submitted form to update a Rating.
     * In case of success, the page redirects to the page displaying all Ratings.
     * Otherwise it redirects to the form to update the Rating.
     * @param id
     * @param rating
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Ratings in case of success, otherwise the form to update a Rating.
     */

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute("ratings") Rating rating,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Rating is not valid for updating with id: " + id);
            return "rating/update";
        }
        ratingService.updateRatingById(id, rating);
        logger.info(authentication.getName() + " has updating rating with id " + id);
        return "redirect:/rating/list";
    }

    /**
     * This method is used to delete a Rating.
     * @param id
     * @param model
     * @param authentication
     * @return It returns the updated list of Ratings after the deletion.
     */

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        ratingService.deleteRatingById(id);
        logger.info(authentication.getName() + " has deleted Rating with id " + id);
        return "redirect:/rating/list";
    }
}
