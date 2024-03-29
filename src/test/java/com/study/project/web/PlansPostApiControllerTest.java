package com.study.project.web;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Plan;
import com.study.project.domain.plans.PlanRepository;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.PlanSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlansPostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlanRepository planRepository;

    @AfterEach
    public void tearDown() throws Exception {
        planRepository.deleteAll();
    }

    @Test
    public void plan_save() throws Exception {
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

        String url ="http://localhost:" + port +"/api/v1/plans";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Plan> planList = planRepository.findAll();

        Plan plan = planList.get(0);
        assertThat(plan.getTitle()).isEqualTo(title);
        assertThat(plan.getLocation()).isEqualTo(location);
        assertThat(plan.getStartDate()).isEqualTo(startDate);
        assertThat(plan.getEndDate()).isEqualTo(endDate);
        assertThat(plan.getTripState()).isEqualTo(tripState);
        //BigDecimal 값 비교할 때는 isEqualByComparingTo
        assertThat(plan.getBudget()).isEqualByComparingTo(budget);
        assertThat(plan.getDatePlans()).usingRecursiveComparison().isEqualTo(datePlanSaveRequestDtoList);
    }
}
