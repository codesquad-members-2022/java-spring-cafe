package com.kakao.cafe.service;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public int join(Member member) {
        memberRepository.save(member);
        return member.getUserIndex();
    }

    //전체 회원목록 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 index에 해당하는 회원 name을 찾기
    public Member findOne(int memberId) {
        return memberRepository.findByIndex(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않는 회원입니다."));
    }
}
