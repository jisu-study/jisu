package com.study.project.domain.plans;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

    @Column(nullable = false)
    private int trip_state;

    private BigDecimal budget;

    // 다대일 양방향이기 때문에 각 plan 별 datePlan을 저장할 list
    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DatePlan> datePlans = new ArrayList<>();

    public void putDatePlan(DatePlan datePlan){
        this.datePlans.addAll((Collection<? extends DatePlan>) datePlan);
    }

    @Builder
    public Plan(String title, String location, Date start_date, Date end_date, int trip_state, BigDecimal budget){
        this.title = title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.trip_state = trip_state;
        this.budget = budget;
    }
}
