package com.study.project.web;

import com.study.project.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PlanService planService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/planboard")
    public String planBoard(Model model){
        model.addAttribute("plans", planService.findAllDesc());
        return "plan-board";
    }

    @GetMapping("/plans/save")
    public String planSave(){
        return "plan";
    }
}
