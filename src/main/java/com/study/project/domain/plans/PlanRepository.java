package com.study.project.domain.plans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query(value = "SELECT p FROM Plan p ORDER BY p.id DESC", nativeQuery = true)
    List<Plan> findAllDesc();
}
