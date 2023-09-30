package com.study.project.web.dto;


import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

@Getter
public class PlanResponseDto {
    private Long planId;
    private String title;
    private String location;
    private Date startDate;
    private Date endDate;
    private int tripState;
    private BigDecimal budget;
    private List<DatePlan> datePlans = new ArrayList<DatePlan>();

    public PlanResponseDto(Plan plan){
        this.planId = plan.getPlanId();
        this.title = plan.getTitle();
        this.location = plan.getLocation();
        this.startDate = plan.getStartDate();
        this.endDate = plan.getEndDate();
        this.tripState = plan.getTripState();
        this.budget = plan.getBudget();
        this.datePlans = plan.getDatePlans();
    }
}
