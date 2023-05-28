package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.services.BidListService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller for the Bid object.
 * It is annotated with the @Controller annotation to be used with Thymeleaf
 */

@Controller
public class BidListController {

    private final BidListService bidListService;

    private static final Logger logger = LogManager.getLogger(BidListController.class);


    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * This method is used to get a list of all Bids
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Bids
     */

    @GetMapping("/bid/list")
    public String home(Model model, Authentication authentication)
    {
        logger.info(authentication.getName() + " fetches all bids.");
        model.addAttribute("bidList", bidListService.findAllBids());
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        return "/bidList/list";
    }

    /**
     * This method is used to get a page containing the form to add a new Bid.
     * @param bid
     * @param authentication
     * @return an HTML page containing the form to add a new Bid
     */

    @GetMapping("/bid/add")
    public String addBidForm(Bid bid, Authentication authentication) {
        logger.info(authentication.getName() + " fetches page to add bid.");
        return "bidList/add";
    }

    /**
     * This method is used to validate the content of the submitted form to add a new Bid.
     * If the content is valid, then a new Bid is added and the page redirects the user
     * to the page containing all bids.
     * @param bid
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Bids in a case of success, otherwise the form to add a new Bid.
     */

    @PostMapping("/bid/validate")
    public String validate(@Valid @ModelAttribute("bid") Bid bid, Model model, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            System.out.println(result.toString());
            logger.error("Bid is not valid for user " + authentication.getName());
            return "bidList/add";
        }
        bidListService.saveNewBid(bid);
        logger.info(authentication.getName() + " added a new bid." );
        return "redirect:/bid/list";
    }

    /**
     * This method is used to get the form to update a new Bid. The fields inside the form are filled
     * with the values already existing.
     * @param id
     * @param model
     * @param authentication
     * @return an HTML page containing the form to update a Bid.
     */

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Bid bidById = bidListService.getBidById(id);
        model.addAttribute("bidToBeUpdated", bidById);
        logger.info(authentication.getName() + " fetches page to update bid.");
        return "bidList/update";
    }

    /**
     * This method is used to validate the content of the submitted form to update a Bid.
     * In case of success, the page redirects to the page displaying all Bids.
     * Otherwise it redirects to the form to update the Bid.
     * @param id
     * @param bid
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Bids in case of success, otherwise the form to update a Bid.
     */

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@Valid @ModelAttribute("bid") Bid bid, @PathVariable("id") Integer id,
                            BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error("Bid is not valid for user when trying to update: " + authentication.getName() + " for bid with id: " + id);
            return "bidList/update";
        }
        bidListService.updateBid(id, bid);
        logger.info(authentication.getName() + " update a bid with id: " + id);
        return "redirect:/bid/list";
    }

    /**
     * This method is used to delete a Bid.
     * @param id
     * @param model
     * @param authentication
     * @return It returns the updated list of Bids after the deletion.
     */

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        bidListService.deleteBid(id);
        model.addAttribute("bidList", bidListService.findAllBids());
        logger.info(authentication.getName() + " has deleted bid with id: " + id);
        return "redirect:/bid/list";
    }
}
