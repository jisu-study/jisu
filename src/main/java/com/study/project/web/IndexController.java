package com.study.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/plans/board")
    public String planBoard(){
        return "plan-board";
    }

    @GetMapping("/plans/save")
    public String planSave(){
        return "plan";
    }
}
