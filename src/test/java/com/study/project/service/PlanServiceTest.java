package com.study.project.service;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.DatePlanRepository;
import com.study.project.domain.plans.Plan;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.PlanSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private DatePlanRepository datePlanRepository;

    @Autowired
    private PlanService planService;

    @AfterEach
    public void tearDown() throws Exception {
        planRepository.deleteAll();
        datePlanRepository.deleteAll();
    }

    @Test
    public void save_test() throws Exception {
        String title = "대만 여행";
        String location = "타이페이";
        Date startDate = Date.valueOf("2023-03-12");
        Date endDate = Date.valueOf("2023-03-15");
        int tripState = 0;
        BigDecimal budget = BigDecimal.valueOf(798915.00);

        PlanSaveRequestDto requestDto = PlanSaveRequestDto.builder()
                .title(title)
                .location(location)
                .startDate(startDate)
                .endDate(endDate)
                .tripState(tripState)
                .budget(budget).build();

        List<DatePlanSaveRequestDto> datePlanSaveRequestDtoList = new ArrayList<DatePlanSaveRequestDto>();

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

        datePlanSaveRequestDtoList.add(DatePlanSaveRequestDto.builder()
                .date(date1)
                .startTime(startTime1)
                .endTime(endTime1)
                .tourSpot(tourSpot1)
                .content(content1).build());
        datePlanSaveRequestDtoList.add(DatePlanSaveRequestDto.builder()
                .date(date2)
                .startTime(startTime2)
                .endTime(endTime2)
                .tourSpot(tourSpot2)
                .content(content2)
                .cost(cost2).build());

        //Long plan_id = planService.save(requestDto, datePlanSaveRequestDtoList);

        List<Plan> planList = planRepository.findAll();
        List<DatePlan> datePlanList = datePlanRepository.findAll();

        Plan plan = planList.get(0);
        DatePlan datePlan1 = datePlanList.get(0);
        DatePlan datePlan2 = datePlanList.get(1);
        //assertThat(datePlan1.getPlan()).isEqualTo(plan);
        //assertThat(datePlan1.getPlan().getPlanId()).isEqualTo(plan.getPlanId());
        //assertThat(datePlan2.getPlan().getPlanId()).isEqualTo(plan.getPlanId());
        assertThat(plan.getDatePlans()).usingRecursiveComparison().isEqualTo(datePlanList);
        //BigDecimal 값 비교할 때는 isEqualByComparingTo
    }

}
