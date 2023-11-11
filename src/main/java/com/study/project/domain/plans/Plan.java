package com.study.project.domain.plans;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id")//DB column명과 매핑되도록
    private Long planId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

    @Column(nullable = false, name = "trip_state")
    private int tripState;

    private BigDecimal budget;

    // 다대일 양방향이기 때문에 각 plan 별 datePlan을 저장할 list
    //plan은 DatePlan entity에서 Plan field value의 이름
    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatePlan> datePlans = new ArrayList<DatePlan>();

    public void putDatePlan(DatePlan datePlan){
        this.datePlans.add(datePlan);
        datePlan.setPlan(this);
    }

    @Builder
    public Plan(String title, String location, Date startDate, Date endDate, int tripState, BigDecimal budget){
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripState = tripState;
        this.budget = budget;
    }

    public void update(String title, String location, Date startDate, Date endDate, int tripState, BigDecimal budget){
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripState = tripState;
        this.budget = budget;
    }
}
