package com.kakao.cafe.service;

import com.kakao.cafe.domain.Member;
import com.kakao.cafe.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원가입한 이름과 부여한 인덱스로 찾은 이름과 동일해야 한다")
    void join() {
        //given
        Member member = new Member();
        member.setName("Vans1");
        int saveIndex = memberService.join(member);

        //when
        Member findMember = memberService.findOne(saveIndex).get();

        //then
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    @DisplayName("회원가입한 이름 n개와 저장된 회원의 n개가 동일해야한다.")
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("Vans1");
        memberService.join(member1);
        Member member2 = new Member();
        member2.setName("Vans2");
        memberService.join(member2);

        //when
        List<Member> result = memberService.findMembers();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원가입한 이름 갯수와 마지막 가입한 회원에게 부여된 index의 값이 동일해야 한다.")
    void findOne() {
        //given
        Member member1 = new Member();
        member1.setName("Vans1");
        Member member2 = new Member();
        member2.setName("Vans2");

        //when
        memberService.join(member1);
        int result = memberService.join(member2);

        //then
        assertThat(result).isEqualTo(2);
    }
}