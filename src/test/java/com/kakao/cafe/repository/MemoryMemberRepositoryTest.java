package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        //given
        Member member = new Member();
        member.setName("Vans");
        repository.save(member);

        //when
        Member result = repository.findById(member.getId()).get();

        //then
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        //given
        Member member1 = new Member();
        member1.setName("Vans1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("Vans2");
        repository.save(member2);

        //when
        Member result = repository.findByName("Vans1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        //given
        Member member1 = new Member();
        member1.setName("Vans1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("Vans2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

    }
}
