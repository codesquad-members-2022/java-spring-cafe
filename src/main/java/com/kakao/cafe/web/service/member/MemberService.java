package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.JoinResponse;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileChangeResponse;

import java.util.List;
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 임시 조회를 위해 만든 메서드
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(int userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

    public Member join(Member member) {
        return memberRepository.insert(member);
    }

    public Member editProfile(ProfileChangeRequest request) {
        Member findMember = memberRepository.findById(request.getId()).orElseThrow();
        findMember.changeNickName(request.getNickName());
        findMember.changeEmail(request.getEmail());
        return findMember;
    }

    public ProfileChangeResponse getMemberDetails(int id, ProfileChangeRequest request) {
        Member findMember = memberRepository.findById(id).orElseThrow();
        request.enrollInformation(findMember);
        return new ProfileChangeResponse(request);
    }
}
