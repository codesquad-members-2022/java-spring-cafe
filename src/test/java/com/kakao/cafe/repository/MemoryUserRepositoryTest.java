package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {
    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("회원으로 저장한 이름과 부여한 인덱스로 찾은 이름과 동일해야 한다")
    public void saveAndFindByIndex() {
        //given
        User user1 = new User();
        user1.setName("Vans1");
        repository.save(user1);
        User user2 = new User();
        user2.setName("Vans2");
        repository.save(user2);

        //when
        User result1 = repository.findByIndex(user1.getUserIndex()).get();
        User result2 = repository.findByIndex(user2.getUserIndex()).get();

        //then
        assertThat(user1).isEqualTo(result1);
        assertThat(user2).isEqualTo(result2);
    }

    @Test
    @DisplayName("회원가입한 이름 n개와 저장된 회원의 n개가 동일해야한다.")
    public void findAll() {
        //given
        User user1 = new User();
        user1.setName("Vans1");
        repository.save(user1);
        User user2 = new User();
        user2.setName("Vans2");
        repository.save(user2);

        //when
        List<User> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

    }
}
