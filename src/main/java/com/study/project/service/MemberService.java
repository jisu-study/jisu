package com.study.project.service;

import com.study.project.domain.member.Member;
import com.study.project.domain.member.MemberRepository;
import com.study.project.web.dto.member.MemberSaveRequestDto;
import com.study.project.web.dto.member.MemberUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public long save(MemberSaveRequestDto memberSaveRequestDto){
        return memberRepository.save(memberSaveRequestDto.toEntity()).getUserId();
    }

    public Member getUserById(Long userId){
        return memberRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("해당 회원이 없습니다. id="+userId));
    }

    public void update(MemberUpdateRequestDto memberUpdateRequestDto, Member member){
        member.update(memberUpdateRequestDto.getUserPassword(), memberUpdateRequestDto.getUserName());
    }

    public void delete(Long userId){
        memberRepository.deleteById(userId);
    }

}
