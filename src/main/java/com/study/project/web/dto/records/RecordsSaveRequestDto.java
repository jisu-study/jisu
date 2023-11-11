package com.study.project.web.dto.records;

import com.study.project.domain.records.Records;
import com.study.project.web.dto.records.RecordContentSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecordsSaveRequestDto {
    private String recordTitle;

    private String location;
    private Date startDate;
    private Date endDate;
    private List<RecordContentSaveRequestDto> recordContents;

    @Builder
    public RecordsSaveRequestDto(String recordTitle, String location, Date startDate, Date endDate){
        this.recordTitle = recordTitle;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Records toEntity(){
        return Records.builder()
                .recordTitle(recordTitle)
                .location(location)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
