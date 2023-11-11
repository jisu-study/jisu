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

    //로그인 페이지
    @GetMapping("/signin")
    public String logIn() { return "sign-in"; }

    //여행 계획 게시판 페이지
    @GetMapping("/planboard")
    public String planBoard(Model model){
        model.addAttribute("plans", planService.findAllDesc());
        return "plan-board";
    }

    //여행 계획 작성 페이지
    @GetMapping("/plans/save")
    public String planSave(){
        return "plan";
    }

    //여행 계획 조회 페이지
    @GetMapping("/plans/view/{plan_id}")
    public String planView(@PathVariable("plan_id") Long planId, Model model){
        PlanResponseDto planResponseDto = planService.findById(planId);
        model.addAttribute("plan", planResponseDto);

        return "plan-view";
    }

    //여행 계획 수정 페이지
    @GetMapping("/plans/update/{plan_id}")
    public String planUpdate(@PathVariable("plan_id") Long planId, Model model) {
        PlanResponseDto planResponseDto = planService.findById(planId);
        model.addAttribute("plan", planResponseDto);

        return "plan-update";
    }

    //새롭게 추가된 코드
    //여행 기록 작성 페이지
    @GetMapping("/new_travel_record")
    public String newTravelRecord(){
        return "new_travel_record";
    }

    //여행 기록 게시판 페이지
    @GetMapping("/view_record_list")
    public String showViewRecordList(){
        return "view_record_list";
    }

    //여행 기록 세부 내용 조회 페이지
    @GetMapping("/view_detail_record") //나중에 view_record_detail로 바꾸고싶다
    public String showViewDetailRecord(){ //@RequestParam Long recordId, Model model 이거 없어도 되네?
        return "view_detail_record";
    }

    //여행 기록 수정 페이지
    @GetMapping("/update_record")
    public String updateRecord(){
        return "update_record";
    }

    //여행 비용 조회 페이지
    @GetMapping("/view_recordCosts")
    public String showViewRecordCosts(){
        return "cost_table";
    }
}
