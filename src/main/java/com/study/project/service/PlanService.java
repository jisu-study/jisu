package com.study.project.service;

import com.study.project.domain.plans.*;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.PlanListResponseDto;
import com.study.project.web.dto.PlanSaveRequestDto;
//import jakarta.transaction.Transactional; 이거 바꿨다고 다시 저장 안 되진 않겠지
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    //예산 합계 함수 추가해주기
    //@Transactional
    //public BigDecimal costSum(){
    //
    //}
}
