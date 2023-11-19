package com.study.project.web.dto.member;

import com.study.project.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private String userEmail;
    private String userName;

    public MemberResponseDto(Member member){
        this.userEmail = member.getUserEmail();
        this.userName = member.getUserName();
    }

}
