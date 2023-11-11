package com.study.project.domain.plans;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
@NoArgsConstructor
@Entity(name="date_plan")
//table 이름과 entity 이름이 다를 경우 name="DB 테이블명" 추가
//java의 경우 Camel 표기법으로 명명 권고, DB는 _ 사용
public class DatePlan {
    //plan table과 dateplan table 다대일 양방향 mapping 관계
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")//name 값은 table이름_pk이름
    private Plan plan;

    //복합키 구현 복잡해
    //date와 plan_id를 복합키로 설정하는 대신 별도로 date_plan_id를 설정
    //DB 스키마 date_plan table에서 date_plan_id 추가 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_plan_id")
    private Long datePlanId;

    //date와 plan_id를 복합키로 설정하는 대신 별도로 date_plan_id를 설정
    //DB 스키마 date_plan table에서 date_plan_id 추가 필요
    @Column(nullable = false)
    private int date;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "tour_spot")
    private String tourSpot;

    @Column(columnDefinition = "TEXT")
    private String content;

    private BigDecimal cost;

    public void setPlan(Plan plan){
        this.plan = plan;
    }

    @Builder
    public DatePlan(Plan plan, int date, Time startTime, Time endTime, String tourSpot, String content, BigDecimal cost){
        this.plan = plan;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tourSpot = tourSpot;
        this.content = content;
        this.cost = cost;
    }
}
