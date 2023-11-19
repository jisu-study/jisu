package com.study.project.web;

import com.study.project.domain.member.Member;
import com.study.project.domain.member.MemberRepository;
import com.study.project.service.MemberService;
import com.study.project.web.dto.member.MemberSaveRequestDto;
import com.study.project.web.dto.member.MemberUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberApiController {

    @Autowired
    private MemberService memberService;
    private MemberRepository memberRepository;

    @PostMapping("/add-user")
    public Long addUser(@RequestBody MemberSaveRequestDto memberSaveRequestDto){
        return memberService.save(memberSaveRequestDto);
    }

    @GetMapping("/get-user/{userId}")
    public Member getUserById(@PathVariable Long userId){
        return memberService.getUserById(userId);
    }

    @PutMapping("/update-user")
    public void updateUser(@RequestBody MemberUpdateRequestDto memberUpdateRequestDto){
        Member member = memberRepository.findById(1L).orElse((null));
        if(member==null) {
            throw new NullPointerException("member가 비어있습니다.");
        }
        memberService.update(memberUpdateRequestDto,member);
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        memberService.delete(userId);
    }

}
