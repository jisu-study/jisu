package com.study.project.web.dto.records;

import com.study.project.domain.recordContents.RecordContent;
import com.study.project.domain.records.Records;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RecordContentUpdateRequestDto {

    private Long recordContentId;
    private Long recordId;
    private Date date;
    private String content;
    private String hashtag;

    @Builder
    public RecordContentUpdateRequestDto(Long recordContentId, Long recordId, String content, String hashtag, Date date) {
        this.recordContentId = recordContentId;
        this.recordId = recordId;
        this.content = content;
        this.hashtag = hashtag;
        this.date = date;
    }

    public RecordContent toEntity(Records record) {
        return RecordContent.builder()
                .records(record)
                .date(date)
                .content(content)
                .hashtag(hashtag)
                .build();
    }

}