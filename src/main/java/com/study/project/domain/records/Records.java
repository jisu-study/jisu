package com.study.project.domain.records;

import com.study.project.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Records extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordId;

    @Column(length = 255, nullable = false)
    private String recordTitle;

    @Column(nullable=false)
    private String location;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;


    @Builder
    public Records(long recordId, String recordTitle, String location, Date startDate, Date endDate) {
        this.recordId = recordId;
        this.recordTitle = recordTitle;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String recordTitle, String location, Date startDate, Date  endDate) {
        this.recordTitle = recordTitle;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

