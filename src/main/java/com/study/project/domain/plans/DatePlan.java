package com.study.project.domain.plans;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
@NoArgsConstructor
@Entity
public class DatePlan {
    //plan table과 dateplan table 다대일 양방향 mapping 관계
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    //복합키 구현 복잡해
    //date와 plan_id를 복합키로 설정하는 대신 별도로 date_plan_id를 설정
    //DB 스키마 date_plan table에서 date_plan_id 추가 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long date_plan_id;

    //date와 plan_id를 복합키로 설정하는 대신 별도로 date_plan_id를 설정
    //DB 스키마 date_plan table에서 date_plan_id 추가 필요
    @Column(nullable = false)
    private int date;

    private Time start_time;

    private Time end_time;

    private String tour_spot;

    @Column(columnDefinition = "TEXT")
    private String content;

    private BigDecimal cost;

    @Builder
    public DatePlan(Plan plan, int date, Time start_time, Time end_time, String tour_spot, String content, BigDecimal cost){
        this.plan = plan;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.tour_spot = tour_spot;
        this.content = content;
        this.cost = cost;
    }
}
