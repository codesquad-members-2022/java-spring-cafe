package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("회원으로 저장한 이름과 부여한 인덱스로 찾은 이름과 동일해야 한다")
    public void saveAndFindByIndex() {
        //given
        Member member1 = new Member();
        member1.setName("Vans1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("Vans2");
        repository.save(member2);

        //when
        Member result1 = repository.findByIndex(member1.getUserIndex()).get();
        Member result2 = repository.findByIndex(member2.getUserIndex()).get();

        //then
        assertThat(member1).isEqualTo(result1);
        assertThat(member2).isEqualTo(result2);
    }

    @Test
    @DisplayName("회원가입한 이름 n개와 저장된 회원의 n개가 동일해야한다.")
    public void findAll() {
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
