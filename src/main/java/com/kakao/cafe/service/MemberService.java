package com.kakao.cafe.service;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.repository.MemberRepository;
import com.kakao.cafe.repository.MemoryMemberRepository;

import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //회원가입
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    //전체 회원목록 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
}
