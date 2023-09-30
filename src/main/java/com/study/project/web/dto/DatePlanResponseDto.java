package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
public class DatePlanResponseDto {
    private Long datePlanId;
    private Plan plan;
    private int date;
    private Time startTime;
    private Time endTime;
    private String tourSpot;
    private String content;
    private BigDecimal cost;

    public DatePlanResponseDto(DatePlan datePlan){
        this.datePlanId = datePlan.getDatePlanId();
        this.plan = datePlan.getPlan();
        this.date = datePlan.getDate();
        this.startTime = datePlan.getStartTime();
        this.endTime = datePlan.getEndTime();
        this.tourSpot = datePlan.getTourSpot();
        this.content = datePlan.getContent();
        this.cost = datePlan.getCost();
    }
}
