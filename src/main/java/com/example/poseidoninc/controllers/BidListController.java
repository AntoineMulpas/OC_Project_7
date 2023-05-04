package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Bid;
import com.example.poseidoninc.services.BidListService;
import jakarta.validation.Valid;
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

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/bid/list")
    public String home(Model model, Authentication authentication)
    {
        model.addAttribute("bidList", bidListService.findAllBids());
        // TODO: call service find all bids to show to the view
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        return "/bidList/list";
    }

    @GetMapping("/bid/add")
    public String addBidForm(Bid bid) {
        return "bidList/add";
    }

    @PostMapping("/bid/validate")
    public String validate(@Valid @ModelAttribute("bid") Bid bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.saveNewBid(bid);
        return "redirect:/bid/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Bid bidById = bidListService.getBidById(id);
        model.addAttribute("bidToBeUpdated", bidById);
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bid") Bid bid,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        bidListService.updateBid(id, bid);
        return "redirect:/bid/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBid(id);
        model.addAttribute("bidList", bidListService.findAllBids());
        return "redirect:/bid/list";
    }
}
