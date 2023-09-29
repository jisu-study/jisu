package com.study.project.domain.plans;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long imgId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(nullable = false, name="img_name")
    private String imgName;  // 파일 원본명

    @Column(nullable = false, name="img_path")
    private String imgPath;  // 파일 저장 경로

    @Column(nullable = false, name="img_size")
    private Long imgSize;

    public void setPlan(Plan plan){
        this.plan = plan;
    }

    @Builder
    public Image(Plan plan, String imgName, String imgPath, Long imgSize) {
        this.plan = plan;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.imgSize = imgSize;
    }
}