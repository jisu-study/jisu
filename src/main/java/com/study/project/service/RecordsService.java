package com.study.project.service;

import com.study.project.domain.records.Records;
import com.study.project.domain.records.RecordsPostsRepository;
import com.study.project.web.dto.records.RecordsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordsService {

    private final RecordsPostsRepository recordsPostsRepository;

    @Transactional
    public long save(RecordsSaveRequestDto requestDto) {
        return recordsPostsRepository.save(requestDto.toEntity()).getRecordId();
    }

    @Transactional(readOnly = true)
    public List<Records> fetchAllRecords() {
        return recordsPostsRepository.findAll();
    }

    @Transactional
    public Long update(Long recordId, RecordsSaveRequestDto requestDto) {
        Records records = recordsPostsRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +  recordId));
        records.update(requestDto.getRecordTitle(), requestDto.getLocation(), requestDto.getStartDate(), requestDto.getEndDate());
        return recordId;
    }

    @Transactional
    public void delete(Long recordId) {
        Records records = recordsPostsRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +  recordId));

        recordsPostsRepository.deleteById(recordId);
    }

    @Transactional(readOnly = true)
    public Records fetchRecordById(Long recordId) {
        return recordsPostsRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록이 없습니다. id=" + recordId));
    }
}
