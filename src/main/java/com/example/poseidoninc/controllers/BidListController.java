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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class BidListController {
    // TODO: Inject Bid service

    private final BidListService bidListService;

    private static final Logger logger = LogManager.getLogger(BidListController.class);


    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/bid/list")
    public String home(Model model, Authentication authentication)
    {
        logger.info(authentication.getName() + " fetches all bids.");
        model.addAttribute("bidList", bidListService.findAllBids());
        // TODO: call service find all bids to show to the view
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        return "/bidList/list";
    }

    @GetMapping("/bid/add")
    public String addBidForm(Bid bid, Authentication authentication) {
        logger.info(authentication.getName() + " fetches page to add bid.");
        return "bidList/add";
    }

    @PostMapping("/bid/validate")
    public String validate(@Valid @ModelAttribute("bid") Bid bid, BindingResult result, Model model, Authentication authentication) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            logger.error("Bid is not valid for user " + authentication.getName());
            return "bidList/add";
        }
        bidListService.saveNewBid(bid);
        logger.info(authentication.getName() + " added a new bid." );
        return "redirect:/bid/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Bid bidById = bidListService.getBidById(id);
        model.addAttribute("bidToBeUpdated", bidById);
        logger.info(authentication.getName() + " fetches page to update bid.");
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bid") Bid bid,
                             BindingResult result, Model model, Authentication authentication) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            logger.error("Bid is not valid for user when trying to update: " + authentication.getName());
            return "bidList/update";
        }
        bidListService.updateBid(id, bid);
        logger.info(authentication.getName() + " update a bid." );
        return "redirect:/bid/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBid(id);
        model.addAttribute("bidList", bidListService.findAllBids());
        logger.info(authentication.getName() + " has deleted bid.");
        return "redirect:/bid/list";
    }
}
