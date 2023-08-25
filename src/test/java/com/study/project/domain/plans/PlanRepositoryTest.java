package com.study.project.domain.plans;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlanRepositoryTest {

    @Autowired
    PlanRepository planRepository;

    @AfterEach
    public void cleanup() {
        planRepository.deleteAll();
    }

    @Test
    public void plan_save() {
        String title = "대만 여행";
        String location = "타이페이";
        Date startDate = Date.valueOf("2023-03-12");
        Date endDate = Date.valueOf("2023-03-15");
        int tripState = 0;
        BigDecimal budget = BigDecimal.valueOf(798915.00);

        Plan plan = Plan.builder()
                .title(title)
                .location(location)
                .startDate(startDate)
                .endDate(endDate)
                .tripState(tripState)
                .budget(budget).build();

        int date1 = 1;
        Time startTime1 = Time.valueOf("06:10:00");
        Time endTime1 = Time.valueOf("08:00:00");
        String tourSpot1 = "타이페이공항";
        String content1 = "인천공항에서 타이페이공항으로 이동";

        int date2 = 2;
        Time startTime2 = Time.valueOf("17:00:00");
        Time endTime2 = Time.valueOf("19:00:00");
        String tourSpot2 = "타이페이101 전망대";
        String content2 = "야경 구경하기";
        BigDecimal cost2 = BigDecimal.valueOf(23000.00);

        List<DatePlan> datePlans = new ArrayList<>();
        datePlans.add(DatePlan.builder()
                .date(date1)
                .plan(plan)
                .startTime(startTime1)
                .endTime(endTime1)
                .tourSpot(tourSpot1)
                .content(content1).build());
        datePlans.add(DatePlan.builder()
                .date(date2)
                .plan(plan)
                .startTime(startTime2)
                .endTime(endTime2)
                .tourSpot(tourSpot2)
                .content(content2)
                .cost(cost2).build());

        plan.putDatePlan(datePlans.get(0));
        plan.putDatePlan(datePlans.get(1));

        planRepository.save(plan);

        List<Plan> planList = planRepository.findAll();

        Plan plans = planList.get(0);
        assertThat(plans.getTitle()).isEqualTo(title);
        assertThat(plans.getLocation()).isEqualTo(location);
        assertThat(plans.getStartDate()).isEqualTo(startDate);
        assertThat(plans.getEndDate()).isEqualTo(endDate);
        assertThat(plans.getTripState()).isEqualTo(tripState);
        //BigDecimal 값 비교할 때는 isEqualByComparingTo
        assertThat(plans.getBudget()).isEqualByComparingTo(budget);
        assertThat(plans.getDatePlans()).usingRecursiveComparison().isEqualTo(datePlans);
    }
}
