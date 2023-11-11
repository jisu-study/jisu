package com.study.project.domain.recordContents;

import com.study.project.domain.records.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RecordContentRepository extends JpaRepository<RecordContent, Long> {

    List<RecordContent> findByOrderByRecordContentIdDesc();

    Optional<RecordContent> findByRecords_RecordIdAndDate(Long recordId, Date date);

    List<RecordContent> findByRecords(Records records);

}
