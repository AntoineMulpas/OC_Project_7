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

/**
 * This class is a controller for the Trade object.
 * It is annotated with the @Controller annotation to be used with Thymeleaf
 */

@Controller
public class TradeController {
    // TODO: Inject Trade service
    private final TradeService tradeService;

    private static final Logger logger = LogManager.getLogger(TradeController.class);


    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * This method is used to get a list of all Trades
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Trades
     */

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

    /**
     * This method is used to get a page containing the form to add a new Trade.
     * @param bid
     * @param authentication
     * @return an HTML page containing the form to add a new Trade
     */

    @GetMapping("/trade/add")
    public String addUser(Trade bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new trade.");
        return "trade/add";
    }

    /**
     * This method is used to validate the content of the submitted form to add a new Trade.
     * If the content is valid, then a new Trade is added and the page redirects the user
     * to the page containing all trades.
     * @param trade
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Trades in a case of success, otherwise the form to add a new Trade.
     */

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

    /**
     * This method is used to get the form to update a new Trade. The fields inside the form are filled
     * with the values already existing.
     * @param id
     * @param model
     * @param authentication
     * @return an HTML page containing the form to update a Trade.
     */

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Trade tradeById = tradeService.findTradeById(id);
        model.addAttribute("trade", tradeById);
        logger.info(authentication.getName() + " has requested page to update trade.");
        return "trade/update";
    }

    /**
     * This method is used to validate the content of the submitted form to update a Trade.
     * In case of success, the page redirects to the page displaying all Trades.
     * Otherwise it redirects to the form to update the Trade.
     * @param id
     * @param trade
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Trades in case of success, otherwise the form to update a Trade.
     */

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Trade is not valid for updating id " + id);
            return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        logger.info(authentication.getName() + " has updated trade with id " + id);
        return "redirect:/trade/list";
    }

    /**
     * This method is used to delete a Trade.
     * @param id
     * @param model
     * @param authentication
     * @return It returns the updated list of Trades after the deletion.
     */

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        tradeService.deleteTradeById(id);
        logger.info(authentication.getName() + " has deleted trade with id " + id);
        return "redirect:/trade/list";
    }
}
