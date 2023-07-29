package com.study.project.domain.plans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    //@Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    //List<Plan> findAllDesc();
}
