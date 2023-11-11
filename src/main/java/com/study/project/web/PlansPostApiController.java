package com.study.project.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.project.service.PlanService;
import com.study.project.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //수정 기능
    @PutMapping("/api/v1/plans/{plan_id}")
    public Long update(@PathVariable("plan_id") Long planId, @RequestBody ObjectNode requestAll) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        PlanUpdateRequestDto requestDto = mapper.treeToValue(requestAll.get("plan"), PlanUpdateRequestDto.class);
        List<DatePlanSaveRequestDto> dpSaveRequestDtoList = mapper.convertValue(mapper.treeToValue(requestAll.get("datePlans"), List.class), new TypeReference<List<DatePlanSaveRequestDto>>(){});

        return planService.update(planId, requestDto, dpSaveRequestDtoList);
    }

    //삭제 기능
    @DeleteMapping("/api/v1/plans/{plan_id}")
    public Long delete(@PathVariable("plan_id") Long planId){
        planService.delete(planId);
        return planId;
    }
}
