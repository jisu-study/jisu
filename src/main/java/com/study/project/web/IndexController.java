package com.study.project.web;

import com.study.project.service.PlanService;
import com.study.project.web.dto.PlanResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PlanService planService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/signin")
    public String logIn() { return "sign-in"; }

    @GetMapping("/planboard")
    public String planBoard(Model model){
        model.addAttribute("plans", planService.findAllDesc());
        return "plan-board";
    }

    @GetMapping("/plans/save")
    public String planSave(){
        return "plan";
    }

    @GetMapping("/plans/view/{plan_id}")
    public String planView(@PathVariable("plan_id") Long planId, Model model){
        PlanResponseDto planResponseDto = planService.findById(planId);
        model.addAttribute("plan", planResponseDto);

        return "plan-view";
    }

    @GetMapping("/plans/update/{plan_id}")
    public String planUpdate(@PathVariable("plan_id") Long planId, Model model) {
        PlanResponseDto planResponseDto = planService.findById(planId);
        model.addAttribute("plan", planResponseDto);

        return "plan-update";
    }
}
