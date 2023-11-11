package com.study.project.service;

import com.study.project.domain.recordContents.RecordContent;
import com.study.project.domain.recordContents.RecordContentRepository;
import com.study.project.domain.records.Records;
import com.study.project.domain.records.RecordsPostsRepository;
import com.study.project.web.dto.records.RecordContentSaveRequestDto;
import com.study.project.web.dto.records.RecordContentSaveRequestDto;
import com.study.project.web.dto.records.RecordContentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecordContentService {

    private final RecordContentRepository recordContentRepository;
    private final RecordsPostsRepository recordsPostsRepository; // 추가

    @Transactional
    public Long saveContent(RecordContentSaveRequestDto requestDto) {
        Records record = recordsPostsRepository.findById(requestDto.getRecordId())
                .orElseThrow(() -> new IllegalArgumentException("해당 레코드가 없습니다. id=" + requestDto.getRecordId()));
        RecordContent recordContent = requestDto.toEntity(record);
        return recordContentRepository.save(recordContent).getRecordContentId();
    }

    @Transactional(readOnly = true)
    public List<RecordContent> fetchAllContents() {
        return recordContentRepository.findAll();
    }

    @Transactional
    public Long updateContent(Long recordContentId, RecordContentSaveRequestDto requestDto) {
        RecordContent content = recordContentRepository.findById(recordContentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 내용이 없습니다. id=" + recordContentId));
        Records record = recordsPostsRepository.findById(requestDto.getRecordId())
                .orElseThrow(() -> new IllegalArgumentException("해당 레코드가 없습니다. id=" + requestDto.getRecordId()));
        content.update(record, requestDto.getContent(), requestDto.getHashtag(), requestDto.getDate());
        return recordContentId;
    }

    @Transactional
    public void deleteContent(Long recordContentId) {
        RecordContent content = recordContentRepository.findById(recordContentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 내용이 없습니다. id=" + recordContentId));
        recordContentRepository.delete(content);
    }

    @Transactional
    public Long updateRecordContent(Long recordContentId, RecordContentUpdateRequestDto requestDto) {
        RecordContent recordContent = recordContentRepository.findById(recordContentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 내용이 없습니다. id=" + recordContentId));

        Records records = recordsPostsRepository.findById(requestDto.getRecordId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + requestDto.getRecordId()));

        recordContent.update(requestDto.getContent(), requestDto.getHashtag());

        return recordContentId;
    }

    @Transactional(readOnly = true)
    public Long checkRecordContentExists(Long recordId, Date date) {
        Optional<RecordContent> recordContentOpt = recordContentRepository.findByRecords_RecordIdAndDate(recordId, date);

        return recordContentOpt.map(RecordContent::getRecordContentId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<RecordContent> fetchRecordContent(Long recordId) {
        Records records = recordsPostsRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레코드가 없습니다. id=" + recordId));
        return recordContentRepository.findByRecords(records);
    }

    //record id로 삭제
    @Transactional
    public void deleteContentsByRecordId(Long recordId) {

        Records record = recordsPostsRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레코드가 없습니다. id=" + recordId));


        List<RecordContent> contents = recordContentRepository.findByRecords(record);


        recordContentRepository.deleteAll(contents);
    }
}
