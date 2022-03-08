package com.kakao.cafe.config;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.member.MemberRepository;

public class DataGenerator {

    private MemberRepository memberRepository;

    public DataGenerator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        init();
    }

    private void init() {
        initMemberInformation();
    }

    private void initMemberInformation() {
        memberRepository.insert(new Member(1, "jun@naver.com", "1234", "Jun"));
        memberRepository.insert(new Member(2, "minsong@naver.com", "1234", "MinJe"));
        memberRepository.insert(new Member(3, "pio@naver.com", "1234", "Pio"));
        memberRepository.insert(new Member(4, "donggi@naver.com", "1234", "donggi"));
        memberRepository.insert(new Member(5, "Nohri@naver.com", "1234", "Nohri"));
        memberRepository.insert(new Member(6, "Vans@naver.com", "1234", "Vans"));
        memberRepository.insert(new Member(7, "Phill@naver.com", "1234", "Phill"));
        memberRepository.insert(new Member(8, "BC@naver.com", "1234", "BC"));
        memberRepository.insert(new Member(9, "Tany@naver.com", "1234", "Tany"));
        memberRepository.insert((new Member(10, "Hanse@naver.com", "1234", "Hanse")));
    }
}
