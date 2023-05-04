package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.Trade;
import com.example.poseidoninc.services.TradeService;
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
public class TradeController {
    // TODO: Inject Trade service
    private final TradeService tradeService;

    private static final Logger logger = LogManager.getLogger(TradeController.class);


    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, Authentication authentication)
    {
        List <Trade> allTrades = tradeService.getAllTrades();
        model.addAttribute("trades",allTrades);
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        logger.info(authentication.getName() + " has requested list of all trades.");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new trade.");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Trade is not valid.");
            return "trade/add";
        }
        tradeService.saveTrade(trade);
        logger.info(authentication.getName() + " has added a new trade.");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Trade tradeById = tradeService.findTradeById(id);
        model.addAttribute("trade", tradeById);
        logger.info(authentication.getName() + " has requested page to update trade.");
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Trade is not valid for updating id " + id);
        }
        tradeService.updateTrade(id, trade);
        logger.info(authentication.getName() + " has updated trade with id " + id);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        tradeService.deleteTradeById(id);
        logger.info(authentication.getName() + " has deleted trade with id " + id);
        return "redirect:/trade/list";
    }
}
