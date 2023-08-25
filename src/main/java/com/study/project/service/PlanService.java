package com.study.project.service;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.DatePlanRepository;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.PlanSaveRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final DatePlanRepository datePlanRepository;
    //필수항목 기재했을 경우에만 저장이 가능하도록 하는 조건을 추가해줘야함

    @Transactional
    public Long save(PlanSaveRequestDto requestDto, List<DatePlanSaveRequestDto> dpRequestDtoList) {
        for (int i=0; i<dpRequestDtoList.size(); i++) {
            dpRequestDtoList.get(i).setPlan(requestDto.toEntity());
            datePlanRepository.save(dpRequestDtoList.get(i).toEntity());
            requestDto.putDatePlan(dpRequestDtoList.get(i).toEntity());
        }
        return planRepository.save(requestDto.toEntity()).getPlanId();
    }

    //예산 합계 함수 추가해주기
    //@Transactional
    //public BigDecimal costSum(){
    //
    //}
}
