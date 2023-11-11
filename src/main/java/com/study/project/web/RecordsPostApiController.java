package com.study.project.web;

import com.study.project.domain.records.Records;
import com.study.project.service.RecordsService;
import com.study.project.web.dto.records.RecordsSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordsPostApiController {

    @Autowired
    private RecordsService recordsService;

    @GetMapping("/fetch-records")
    public List<Records> fetchAllRecords() {
        return recordsService.fetchAllRecords();
    }

    @GetMapping("/fetch-record/{recordId}")
    public Records fetchRecordById(@PathVariable Long recordId) {
        return recordsService.fetchRecordById(recordId);
    }


    @PostMapping("/add-record")
    public Long createRecord(@RequestBody RecordsSaveRequestDto requestDto) {
        return recordsService.save(requestDto);
    }


    @PutMapping("/update-record/{id}")
    public Long updateRecord(@PathVariable Long id, @RequestBody RecordsSaveRequestDto requestDto) {
        return recordsService.update(id, requestDto);
    }

    @DeleteMapping("/delete-record/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordsService.delete(id);
    }
}
