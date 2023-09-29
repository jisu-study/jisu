package com.study.project.web.dto;

import com.study.project.domain.plans.DatePlan;
import com.study.project.domain.plans.Image;
import com.study.project.domain.plans.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ImageSaveRequestDto {

    private Plan plan;
    private String imgName;  // 파일 원본명
    private String imgPath;  // 파일 저장 경로
    private Long imgSize;

    @Builder
    public ImageSaveRequestDto(Plan plan, String imgName, String imgPath, Long imgSize){
        this.plan = plan;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.imgSize = imgSize;
    }

    public Image toEntity(){
        return Image.builder()
                .plan(plan)
                .imgName(imgName)
                .imgPath(imgPath)
                .imgSize(imgSize)
                .build();
    }
}