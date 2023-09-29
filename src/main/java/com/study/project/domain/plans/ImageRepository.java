package com.study.project.domain.plans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT p FROM Image p ORDER BY p.id DESC", nativeQuery = true)
    List<Image> findAllDesc();
}
