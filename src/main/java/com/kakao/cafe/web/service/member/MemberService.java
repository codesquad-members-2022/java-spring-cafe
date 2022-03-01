package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;
import com.kakao.cafe.web.controller.member.dto.JoinRequest;
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

    /**
     * 임시 조회를 위해 만든 메서드
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public int size() {
        return memberRepository.size();
    }

    public Optional<Member> findById(Long userId) {
        return memberRepository.findById(userId);
    }

    public Member join(JoinRequest request) {
        return memberRepository.insert(request.toEntity());
    }
}
