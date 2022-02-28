package com.kakao.cafe.web.service.member;

import com.kakao.cafe.core.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
