package com.study.project.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@NoArgsConstructor
public class DatePlanSaveRequestDto {
    private Plan plan;
    private int date;
    private Time startTime;
    private Time endTime;
    private String tourSpot;
    private String content;
    private BigDecimal cost;

    @Builder
    public DatePlanSaveRequestDto(int date, Time startTime, Time endTime, String tourSpot, String content, BigDecimal cost){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tourSpot = tourSpot;
        this.content = content;
        this.cost = cost;
    }

    public void setPlan(Plan plan){
        this.plan = plan;
    }

    public DatePlan toEntity(){
        return DatePlan.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .tourSpot(tourSpot)
                .content(content)
                .cost(cost)
                .build();
    }
}
