package com.study.project.web.dto.member;

import com.study.project.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String userEmail;
    private String userPassword;
    private String userName;

    @Builder
    public MemberSaveRequestDto(String userEmail, String userPassword, String userName){
        this.userEmail=userEmail;
        this.userPassword=userPassword;
        this.userName=userName;
    }

    public Member toEntity(){
        return Member.builder()
                .userEmail(userEmail)
                .userPassword(userPassword)
                .userName(userName)
                .build();
    }

}
