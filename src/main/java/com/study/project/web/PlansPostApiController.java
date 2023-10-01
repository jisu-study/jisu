package com.study.project.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.project.service.PlanService;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.PlanResponseDto;
import com.study.project.web.dto.PlanSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PlansPostApiController {

    private final PlanService planService;

    @PostMapping("/api/v1/plans")
    public Long save(@RequestBody ObjectNode requestAll) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        PlanSaveRequestDto requestDto = mapper.treeToValue(requestAll.get("plan"), PlanSaveRequestDto.class);
        List<DatePlanSaveRequestDto> dpSaveRequestDtoList = mapper.convertValue(mapper.treeToValue(requestAll.get("datePlans"), List.class), new TypeReference<List<DatePlanSaveRequestDto>>(){});

        return planService.save(requestDto, dpSaveRequestDtoList);
    }

    //삭제 기능
    @DeleteMapping("/api/v1/plans/{plan_id}")
    public Long delete(@PathVariable("plan_id") Long planId){
        planService.delete(planId);
        return planId;
    }
}
