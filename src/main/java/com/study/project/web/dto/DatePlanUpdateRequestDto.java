package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
@NoArgsConstructor
public class DatePlanUpdateRequestDto {
    private int date;
    private Time startTime;
    private Time endTime;
    private String tourSpot;
    private String content;
    private BigDecimal cost;

    @Builder
    public DatePlanUpdateRequestDto(int date, Time startTime, Time endTime, String tourSpot, String content, BigDecimal cost){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tourSpot = tourSpot;
        this.content = content;
        this.cost = cost;
    }
}
