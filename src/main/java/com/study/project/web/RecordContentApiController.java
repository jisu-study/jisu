package com.study.project.web;

import com.study.project.domain.recordContents.RecordContent;
import com.study.project.domain.recordContents.RecordContentRepository;
import com.study.project.domain.records.RecordsPostsRepository;
import com.study.project.service.RecordContentService;
import com.study.project.web.dto.records.RecordContentSaveRequestDto;
import com.study.project.web.dto.records.RecordContentUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RecordContentApiController {

    private final RecordContentRepository recordContentRepository;
    private final RecordContentService recordContentService;
    private final RecordsPostsRepository recordsPostsRepository;

    @Autowired
    public RecordContentApiController(RecordContentRepository recordContentRepository,
                                      RecordContentService recordContentService,
                                      RecordsPostsRepository recordsPostsRepository) { // 수정된 부분
        this.recordContentRepository = recordContentRepository;
        this.recordContentService = recordContentService;
        this.recordsPostsRepository = recordsPostsRepository; // 추가된 부분
    }

    @PostMapping("/add-record-content")
    public Long createRecordContent(@RequestBody RecordContentSaveRequestDto requestDto) {
        return recordContentService.saveContent(requestDto);
    }

    @PutMapping("/update-record-content/{recordContentId}")
    public Long updateRecordContent(@PathVariable Long recordContentId, @RequestBody RecordContentUpdateRequestDto requestDto) {
        return recordContentService.updateRecordContent(recordContentId, requestDto);
    }

    @GetMapping("/check-record-content")
    public ResponseEntity<?> checkRecordContentExists(@RequestParam Long recordId, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        Long recordContentId = recordContentService.checkRecordContentExists(recordId, date);

        if (recordContentId != null) {
            return new ResponseEntity<>(recordContentId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/fetch-record-content/{recordId}")
    public ResponseEntity<List<RecordContent>> fetchRecordContent(@PathVariable Long recordId) {
        List<RecordContent> recordContents = recordContentService.fetchRecordContent(recordId);
        return new ResponseEntity<>(recordContents, HttpStatus.OK);
    }

    @DeleteMapping("/delete-record-content/{recordId}")
    public ResponseEntity<?> deleteRecordContentByRecordId(@PathVariable Long recordId) {
        try {
            recordContentService.deleteContentsByRecordId(recordId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}