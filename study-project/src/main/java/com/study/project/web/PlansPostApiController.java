package com.study.project.web;

import com.study.project.service.PlanService;
import com.study.project.web.dto.PlanSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlansPostApiController {

    private final PlanService planService;

    @PostMapping("/api/v1/plans")
    public Long save(@RequestBody PlanSaveRequestDto requestDto){
        return planService.save(requestDto);
    }
}
