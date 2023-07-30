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
        Date start_date = Date.valueOf("2023-03-12");
        Date end_date = Date.valueOf("2023-03-15");
        int trip_state = 0;
        BigDecimal budget = BigDecimal.valueOf(798915.00);

        Plan plan = Plan.builder()
                .title(title)
                .location(location)
                .start_date(start_date)
                .end_date(end_date)
                .trip_state(trip_state)
                .budget(budget).build();

        int date1 = 1;
        Time start_time1 = Time.valueOf("06:10:00");
        Time end_time1 = Time.valueOf("08:00:00");
        String tour_spot1 = "타이페이공항";
        String content1 = "인천공항에서 타이페이공항으로 이동";

        int date2 = 2;
        Time start_time2 = Time.valueOf("17:00:00");
        Time end_time2 = Time.valueOf("19:00:00");
        String tour_spot2 = "타이페이101 전망대";
        String content2 = "야경 구경하기";
        BigDecimal cost2 = BigDecimal.valueOf(23000.00);

        List<DatePlan> datePlans = new ArrayList<>();
        datePlans.add(DatePlan.builder()
                .date(date1)
                .plan(plan)
                .start_time(start_time1)
                .end_time(end_time1)
                .tour_spot(tour_spot1)
                .content(content1).build());
        datePlans.add(DatePlan.builder()
                .date(date2)
                .plan(plan)
                .start_time(start_time2)
                .end_time(end_time2)
                .tour_spot(tour_spot2)
                .content(content2)
                .cost(cost2).build());

        plan.putDatePlan(datePlans.get(0));
        plan.putDatePlan(datePlans.get(1));

        planRepository.save(plan);

        List<Plan> planList = planRepository.findAll();

        Plan plans = planList.get(0);
        assertThat(plans.getTitle()).isEqualTo(title);
        assertThat(plans.getLocation()).isEqualTo(location);
        assertThat(plans.getStart_date()).isEqualTo(start_date);
        assertThat(plans.getEnd_date()).isEqualTo(end_date);
        assertThat(plans.getTrip_state()).isEqualTo(trip_state);
        //BigDecimal 값 비교할 때는 isEqualByComparingTo
        assertThat(plans.getBudget()).isEqualByComparingTo(budget);
        assertThat(plans.getDatePlans()).usingRecursiveComparison().isEqualTo(datePlans);
    }
}
