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
    private Date startDate;
    private Date endDate;
    private int tripState;
    private BigDecimal budget;
    private List<DatePlan> datePlans = new ArrayList<DatePlan>();


    @Builder
    public PlanSaveRequestDto(String title, String location, Date startDate, Date endDate, int tripState, BigDecimal budget){
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripState = tripState;
        this.budget = budget;
        this.datePlans = new ArrayList<DatePlan>();
    }

    public Plan toEntity(){
        return Plan.builder()
                .title(title)
                .location(location)
                .startDate(startDate)
                .endDate(endDate)
                .tripState(tripState)
                .budget(budget)
                .build();
    }
}
