package com.kakao.cafe.service;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    //전체 회원목록 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 id에 해당하는 회원 name을 찾기
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
