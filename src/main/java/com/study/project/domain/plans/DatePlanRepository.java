package com.study.project.domain.plans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//<entity 타입, PK 타입>
public interface DatePlanRepository extends JpaRepository<DatePlan, Long> {
    @Query(value = "SELECT p FROM DatePlan p ORDER BY p.id DESC", nativeQuery = true)
    List<DatePlan> findAllDesc();
}
