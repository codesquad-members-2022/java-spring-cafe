package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    UserRepository repository = new UserMemoryRepository();

    @Test
    @DisplayName("데이터를 저장한다.")
    void save() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");

        User searchedUser = repository.save(user);

        assertThat(user).isSameAs(searchedUser);
    }

    @Test
    @DisplayName("id 값으로 데이터 조회")
    void findById() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        repository.save(user);

        User result = repository.findById(1L).get();

        assertThat(result.getUserId()).isEqualTo("forky");
        assertThat(result.getUsername()).isEqualTo("퐄퐄퐄");
    }

    @Test
    @DisplayName("유저 id 값으로 데이터 조회")
    void findByUserId() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        repository.save(user);

        User result = repository.findByUserId("forky").get();

        assertThat(result.getUserId()).isEqualTo("forky");
        assertThat(result.getUsername()).isEqualTo("퐄퐄퐄");
    }
}
