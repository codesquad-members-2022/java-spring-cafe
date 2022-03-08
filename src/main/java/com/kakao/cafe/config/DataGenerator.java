package com.kakao.cafe.config;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DataGenerator {

    @Autowired
    private MemberRepository memberRepository;

    public DataGenerator() {
        init();
    }

    private void init() {
        initMemberInformation();
    }

    private void initMemberInformation() {
        memberRepository.insert(new Member(1L, "jun@naver.com", "1234", "Jun"));
        memberRepository.insert(new Member(2L, "minsong@naver.com", "1234", "MinJe"));
        memberRepository.insert(new Member(3L, "pio@naver.com", "1234", "Pio"));
        memberRepository.insert(new Member(4L, "donggi@naver.com", "1234", "donggi"));
        memberRepository.insert(new Member(5L, "Nohri@naver.com", "1234", "Nohri"));
        memberRepository.insert(new Member(6L, "Vans@naver.com", "1234", "Vans"));
        memberRepository.insert(new Member(7L, "Phill@naver.com", "1234", "Phill"));
        memberRepository.insert(new Member(8L, "BC@naver.com", "1234", "BC"));
        memberRepository.insert(new Member(9L, "Tany@naver.com", "1234", "Tany"));
        memberRepository.insert((new Member(10L, "Hanse@naver.com", "1234", "Hanse")));
    }
}
