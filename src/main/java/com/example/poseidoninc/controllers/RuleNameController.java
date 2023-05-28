package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.RuleName;
import com.example.poseidoninc.services.RuleNameService;
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
 * This class is a controller for the RuleName object.
 * It is annotated with the @Controller annotation to be used with Thymeleaf
 */
@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);


    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * This method is used to get a list of all RuleNames
     * @param model
     * @param authentication
     * @return an HTML page with a list of all RulesNames
     */

    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication authentication)
    {
        // TODO: find all RuleName, add to model
        List <RuleName> allRules = ruleNameService.getAllRules();
        model.addAttribute("rules", allRules);
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        logger.info(authentication.getName() + " has requested list of all rules.");
        return "ruleName/list";
    }

    /**
     * This method is used to get a page containing the form to add a new RuleName.
     * @param bid
     * @param authentication
     * @return an HTML page containing the form to add a new RuleName
     */

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new rule.");
        return "ruleName/add";
    }

    /**
     * This method is used to validate the content of the submitted form to add a new RuleName.
     * If the content is valid, then a new RuleName is added and the page redirects the user
     * to the page containing all rule names.
     * @param ruleName
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all RuleNames in a case of success, otherwise the form to add a new RuleName.
     */

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": RuleName is not valid.");
            return "ruleName/add";
        }
        ruleNameService.saveRule(ruleName);
        logger.info(authentication.getName() + " has added a new rule.");
        return "redirect:/ruleName/list";
    }

    /**
     * This method is used to get the form to update a new RuleName. The fields inside the form are filled
     * with the values already existing.
     * @param id
     * @param model
     * @param authentication
     * @return an HTML page containing the form to update a RuleName.
     */

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleName rule = ruleNameService.findRuleById(id);
        model.addAttribute("ruleName", rule);
        logger.info(authentication.getName() + " has requested page to update Rule.");
        return "ruleName/update";
    }

    /**
     * This method is used to validate the content of the submitted form to update a RuleName.
     * In case of success, the page redirects to the page displaying all RuleNames.
     * Otherwise it redirects to the form to update the RuleName.
     * @param id
     * @param ruleName
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all RuleNames in case of success, otherwise the form to update a RuleName.
     */

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleName") RuleName ruleName,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error(authentication.getName() + ": Rule is not valid for id" + id);
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(id, ruleName);
        logger.info(authentication.getName() + " has update Rule with id " + id);
        return "redirect:/ruleName/list";
    }

    /**
     * This method is used to delete a RuleName.
     * @param id
     * @param model
     * @param authentication
     * @return It returns the updated list of RuleNames after the deletion.
     */

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        ruleNameService.deleteRuleNameById(id);
        logger.info(authentication.getName() + " has deleted Rule with id" + id);
        return "redirect:/ruleName/list";
    }
}
