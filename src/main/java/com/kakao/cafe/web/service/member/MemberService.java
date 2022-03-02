package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
import com.kakao.cafe.web.controller.member.dto.ProfileFormRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final EntityCheckManager entityCheckManager;

    @Autowired
    public MemberService(MemberRepository memberRepository, EntityCheckManager entityCheckManager) {
        this.memberRepository = memberRepository;
        this.entityCheckManager = entityCheckManager;
    }

    /**
     * 임시 조회를 위해 만든 메서드
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long userId) {
        return memberRepository.findById(userId).orElseThrow();
    }

    public Member join(JoinRequest request) {
        return memberRepository.insert(request.toEntity());
    }

    public Member edit(ProfileFormRequest request) {
        Member findMember = memberRepository.findById(request.getId()).orElseThrow();
        findMember.changeNickName(request.getNickName());
        findMember.changeEmail(request.getEmail());
        return findMember;
    }
}
