package com.study.project.web.dto.records;

//import com.study.project.domain.recordCosts.RecordCost;
import com.study.project.domain.records.Records;
import lombok.Getter;

import java.util.Date;

@Getter
public class RecordsResponseDto {

    private long recordId;
    //private RecordCost cost_id;
    private String recordTitle;
    private String location;
    private Date startDate;
    private Date endDate;

    public RecordsResponseDto(Records entity) {
        this.recordId =entity.getRecordId();
        this.recordTitle = entity.getRecordTitle();
        this.location = entity.getLocation();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
    }
}
