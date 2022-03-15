package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findById(Integer userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

    public Member join(Member member) {
        return memberRepository.persist(member);
    }

    public Member editProfile(ProfileChangeRequest request) {
        Member findMember = memberRepository.findById(request.getId()).orElseThrow();
        findMember.changeNickName(request.getNickName());
        findMember.changeEmail(request.getEmail());
        memberRepository.update(findMember);
        return findMember;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
