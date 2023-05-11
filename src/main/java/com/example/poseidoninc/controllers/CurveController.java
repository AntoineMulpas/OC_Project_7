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

@Controller
public class CurveController {
    // TODO: Inject Curve Point service

    private final CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);


    @Autowired
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

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

    @GetMapping("/curvePoint/add")
    public String addForm(CurvePoint bid, Authentication authentication) {
        logger.info(authentication.getName() + " has requested page to add new curve.");
        return "curvePoint/add";
    }

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

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint1 = curvePointService.findById(id);
        model.addAttribute("curvePoint", curvePoint1);
        logger.info(authentication.getName() + " has requested page to update curve.");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            logger.error("Curve is not valid for id " + id + " for user " + authentication);
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        logger.info(authentication.getName() + " has update curve with id " + id);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        curvePointService.deleteCurvePointById(id);
        logger.info(authentication.getName() + " has delete Curve with id " + id);
        return "redirect:/curvePoint/list";
    }
}
