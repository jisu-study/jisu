package com.study.project.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.project.service.PlanService;
import com.study.project.web.dto.DatePlanSaveRequestDto;
import com.study.project.web.dto.ImageSaveRequestDto;
import com.study.project.web.dto.PlanSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PlansPostApiController {

    private final PlanService planService;

    /* 계획 저장도 안 될 경우를 생각해서 보관...
    @PostMapping("/api/v1/plans")
    public Long save(@RequestBody ObjectNode requestAll) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        PlanSaveRequestDto requestDto = mapper.treeToValue(requestAll.get("plan"), PlanSaveRequestDto.class);
        List<DatePlanSaveRequestDto> dpSaveRequestDtoList = mapper.convertValue(mapper.treeToValue(requestAll.get("datePlans"), List.class), new TypeReference<List<DatePlanSaveRequestDto>>(){});

        List<ImageSaveRequestDto> imageSaveRequestDtoList = new ArrayList<ImageSaveRequestDto>();
        //이미지 업로드 처리 코드 추가 필요
        if (requestAll.has("image")) {
            imageSaveRequestDtoList = mapper.convertValue(mapper.treeToValue(requestAll.get("images"), List.class), new TypeReference<List<ImageSaveRequestDto>>(){});
        }

        return planService.save(requestDto, dpSaveRequestDtoList, imageSaveRequestDtoList);
    }*/

    @PostMapping(value = "/api/v1/plans", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Long save(
            @RequestPart("image") List<MultipartFile> images,
            @RequestParam("plan") String planString,
            @RequestParam("datePlans") String datePlanString
    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        PlanSaveRequestDto requestDto = mapper.readValue(planString, PlanSaveRequestDto.class);
        List<DatePlanSaveRequestDto> dpSaveRequestDtoList = mapper.readValue(datePlanString, new TypeReference<List<DatePlanSaveRequestDto>>(){});

        List<ImageSaveRequestDto> imageSaveRequestDtoList = new ArrayList<ImageSaveRequestDto>();

        for (MultipartFile file : images) {
            ImageSaveRequestDto dto = new ImageSaveRequestDto();
            System.out.println(file.getOriginalFilename());
            // ImageSaveRequestDto의 필드에 파일 정보를 설정
            dto = ImageSaveRequestDto.builder()
                    .imgName(file.getOriginalFilename())
                    .imgPath("/img/")
                    .imgSize(file.getSize()).build();
            System.out.println(dto.getImgName());
            imageSaveRequestDtoList.add(dto);
        }

        // Service를 호출하여 이미지 저장
        return planService.save(requestDto, dpSaveRequestDtoList, imageSaveRequestDtoList);
    }
}
