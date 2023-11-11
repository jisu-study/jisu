package com.study.project.domain.records;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsPostsRepository extends JpaRepository<Records, Long> {

    List<Records> findByOrderByRecordIdDesc();
}
