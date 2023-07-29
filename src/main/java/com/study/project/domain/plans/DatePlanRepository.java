package com.study.project.domain.plans;

import org.springframework.data.jpa.repository.JpaRepository;

//<entity 타입, PK 타입>
public interface DatePlanRepository extends JpaRepository<DatePlan, Long> {

}
