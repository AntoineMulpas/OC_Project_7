package com.example.poseidoninc.controllers;

import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.services.CurvePointService;
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
 * This class is a controller for the Curve object.
 * It is annotated with the @Controller annotation to be used with Thymeleaf
 */

@Controller
public class CurveController {
    // TODO: Inject Curve Point service

    private final CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);


    @Autowired
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * This method is used to get a list of all Curves
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Curves
     */

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication)
    {
        List <CurvePoint> curvePointList = curvePointService.getAllCurvePoint();
        model.addAttribute("curvePoint", curvePointList);
        boolean admin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("admin", admin);
        logger.info(authentication.getName() + " has requested all curves.");
        return "curvePoint/list";
    }

    /**
     * This method is used to get a page containing the form to add a new Curve.
     * @param bid
     * @param authentication
     * @return an HTML page containing the form to add a new Curve
     */

    @GetMapping("/curvePoint/add")
    public String addForm(CurvePoint bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new curve.");
        return "curvePoint/add";
    }

    /**
     * This method is used to validate the content of the submitted form to add a new Curve.
     * If the content is valid, then a new Curve is added and the page redirects the user
     * to the page containing all curves.
     * @param curvePoint
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Curves in a case of success, otherwise the form to add a new Curve.
     */

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model, Authentication authentication) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (result.hasErrors()) {
            logger.error("CurvePoint is not valid for user " + authentication.getName());
            return "curvePoint/add";
        }
        curvePointService.addACurvePoint(curvePoint);
        logger.info(authentication.getName() + " has added a new curve.");
        return "redirect:/curvePoint/list";
    }

    /**
     * This method is used to get the form to update a new Curve. The fields inside the form are filled
     * with the values already existing.
     * @param id
     * @param model
     * @param authentication
     * @return an HTML page containing the form to update a Curve.
     */


    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint1 = curvePointService.findById(id);
        model.addAttribute("curvePoint", curvePoint1);
        logger.info(authentication.getName() + " has requested page to update curve.");
        return "curvePoint/update";
    }

    /**
     * This method is used to validate the content of the submitted form to update a Curve.
     * In case of success, the page redirects to the page displaying all Curves.
     * Otherwise it redirects to the form to update the Curve.
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @param authentication
     * @return an HTML page with a list of all Curves in case of success, otherwise the form to update a Curve.
     */

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@Valid CurvePoint curvePoint,
                             BindingResult result, @PathVariable("id") Integer id, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error("Curve is not valid for id " + id + " for user " + authentication);
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        logger.info(authentication.getName() + " has update curve with id " + id);
        return "redirect:/curvePoint/list";
    }

    /**
     * This method is used to delete a Curve.
     * @param id
     * @param model
     * @param authentication
     * @return It returns the updated list of Curves after the deletion.
     */

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        curvePointService.deleteCurvePointById(id);
        logger.info(authentication.getName() + " has delete Curve with id " + id);
        return "redirect:/curvePoint/list";
    }
}
