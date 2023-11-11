package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlanUpdateRequestDto {
    private String title;
    private String location;
    private Date startDate;
    private Date endDate;
    private int tripState;
    private BigDecimal budget;
    private List<DatePlan> datePlans = new ArrayList<DatePlan>();


    @Builder
    public PlanUpdateRequestDto(String title, String location, Date startDate, Date endDate, int tripState, BigDecimal budget, List<DatePlan> datePlans){
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripState = tripState;
        this.budget = budget;
        this.datePlans = datePlans;
    }
}
