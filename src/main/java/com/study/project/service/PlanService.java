package com.study.project.service;

import com.study.project.domain.plans.*;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.ImageSaveRequestDto;
import com.study.project.web.dto.PlanListResponseDto;
import com.study.project.web.dto.PlanSaveRequestDto;
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
    private final ImageRepository imageRepository;
    //필수항목 기재했을 경우에만 저장이 가능하도록 하는 조건을 추가해줘야함

    public void processDTOs() {

    }

    @Transactional
    public Long save(PlanSaveRequestDto requestDto, List<DatePlanSaveRequestDto> dpRequestDtoList, List<ImageSaveRequestDto> imageSaveRequestDtoList) {
        Plan plan = requestDto.toEntity();
        planRepository.save(plan);

        for (DatePlanSaveRequestDto dpRequestDto : dpRequestDtoList) {
            DatePlan datePlan = dpRequestDto.toEntity();
            datePlan.setPlan(plan);
            plan.putDatePlan(datePlan);
            datePlanRepository.save(datePlan);
        }

        if (imageSaveRequestDtoList != null && !imageSaveRequestDtoList.isEmpty()) {
            for (ImageSaveRequestDto imgRequestDto : imageSaveRequestDtoList) {
                Image image = imgRequestDto.toEntity();
                image.setPlan(plan);
                plan.putImage(image);
                System.out.println(image.getImgName());
                imageRepository.save(image);
            }
        }

        return plan.getPlanId();
    }

    @Transactional(readOnly = true)
    public List<PlanListResponseDto> findAllDesc() {
        return planRepository.findAllDesc().stream()
                .map(PlanListResponseDto::new).collect(Collectors.toList());
    }
}
