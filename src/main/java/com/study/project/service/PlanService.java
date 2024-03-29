package com.study.project.service;

import com.study.project.domain.plans.*;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final DatePlanRepository datePlanRepository;
    //필수항목 기재했을 경우에만 저장이 가능하도록 하는 조건을 추가해줘야함

    @Transactional
    public Long save(PlanSaveRequestDto requestDto, List<DatePlanSaveRequestDto> dpRequestDtoList) {
        Plan plan = requestDto.toEntity();
        planRepository.save(plan);

        for (DatePlanSaveRequestDto dpRequestDto : dpRequestDtoList) {
            DatePlan datePlan = dpRequestDto.toEntity();
            datePlan.setPlan(plan);
            plan.putDatePlan(datePlan);
            datePlanRepository.save(datePlan);
        }

        return plan.getPlanId();
    }

    @Transactional(readOnly = true)
    public List<PlanListResponseDto> findAllDesc() {
        return planRepository.findAllDesc().stream()
                .map(PlanListResponseDto::new).collect(Collectors.toList());
    }

    public PlanResponseDto findById (Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(()->new
                IllegalArgumentException("해당 게시글이 없습니다. plan_id=" + planId));
        PlanResponseDto planResponseDto = new PlanResponseDto(plan);

        return planResponseDto;
    }

    //수정
    @Transactional
    public Long update(Long planId, PlanUpdateRequestDto requestDto, List<DatePlanSaveRequestDto> dpRequestDtoList) {
        Plan plan = planRepository.findById(planId).orElseThrow(()->new
                IllegalArgumentException("해당 게시글이 없습니다. plan_id="+ planId));

        List<DatePlan> datePlans = new ArrayList<DatePlan>();

        plan.getDatePlans().clear();
        for (DatePlanSaveRequestDto dpRequestDto : dpRequestDtoList) {
            DatePlan datePlan = dpRequestDto.toEntity();
            datePlan.setPlan(plan);
            plan.putDatePlan(datePlan);
            datePlanRepository.save(datePlan);
        }

        plan.update(requestDto.getTitle(), requestDto.getLocation(), requestDto.getStartDate(), requestDto.getEndDate(), requestDto.getTripState(), requestDto.getBudget());

        return planId;
    }

    //삭제
    @Transactional
    public void delete(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. plan_id=" + planId));

        planRepository.delete(plan);
    }
}
