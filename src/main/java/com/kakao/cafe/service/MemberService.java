package com.kakao.cafe.service;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //회원 id에 해당하는 회원 name을 찾기
    public Optional<Member> findOne(int memberId) {
        return memberRepository.findByIndex(memberId);
    }
}
