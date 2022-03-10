package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("donggi 라는 이름을 가진 user 객체를 저장하고 이름이 donggi인 객체를 찾았을 때 있으면 테스트가 성공한다")
    void findByNameTest() {
        User user1 = new User("donggi", "donggi@codesquad.kr", "1234%12");
        repository.save(user1);

        User user2 = new User("honux", "honux@codesquad.kr", "432536%12");
        repository.save(user2);

        User result = repository.findByName("donggi").get();

        assertThat(result).isEqualTo(user1);
    }

    @Test
    @DisplayName("2개의 user 객체가 저장된 유저 List의 사이즈가 2이면 테스트가 성공한다")
    void findAllTest() {
        User user1 = new User("donggi", "donggi@codesquad.kr", "1234%12");
        repository.save(user1);

        User user2 = new User("honux", "honux@codesquad.kr", "432536%12");
        repository.save(user2);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}