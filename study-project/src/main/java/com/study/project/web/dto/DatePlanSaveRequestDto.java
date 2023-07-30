package com.study.project.web.dto;

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
    private Time start_time;
    private Time end_time;
    private String tour_spot;
    private String content;
    private BigDecimal cost;

    @Builder
    public DatePlanSaveRequestDto(Plan plan, int date, Time start_time, Time end_time, String tour_spot, String content, BigDecimal cost){
        this.plan = plan;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.tour_spot = tour_spot;
        this.content = content;
        this.cost = cost;
    }

    public DatePlan toEntity(){
        return DatePlan.builder()
                .plan(plan)
                .date(date)
                .start_time(start_time)
                .end_time(end_time)
                .tour_spot(tour_spot)
                .content(content)
                .cost(cost)
                .build();
    }
}
