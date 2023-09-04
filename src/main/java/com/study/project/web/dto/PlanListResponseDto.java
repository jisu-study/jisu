package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PlanListResponseDto {
    private Long planId;
    private String title;
    private String location;
    private Date startDate;
    private Date endDate;
    private int tripState;
    private BigDecimal budget;
    private List<DatePlan> datePlans = new ArrayList<DatePlan>();

    public PlanListResponseDto(Plan entity) {
        this.planId = entity.getPlanId();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.tripState = entity.getTripState();
        this.budget = entity.getBudget();
        this.datePlans = entity.getDatePlans();
    }
}
