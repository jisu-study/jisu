package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlanSaveRequestDto {
    private String title;
    private String location;
    private Date start_date;
    private Date end_date;
    private int trip_state;
    private BigDecimal budget;
    private List<DatePlanSaveRequestDto> datePlanSaveRequestDtos;

    @Builder
    public PlanSaveRequestDto(String title, String location, Date start_date, Date end_date, int trip_state, BigDecimal budget, List<DatePlanSaveRequestDto> datePlanSaveRequestDtos){
        this.title = title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.trip_state = trip_state;
        this.budget = budget;
        this.datePlanSaveRequestDtos = datePlanSaveRequestDtos;
    }

    public Plan toEntity(){
        return Plan.builder()
                .title(title)
                .location(location)
                .start_date(start_date)
                .end_date(end_date)
                .trip_state(trip_state)
                .budget(budget)
                .build();
    }
}
