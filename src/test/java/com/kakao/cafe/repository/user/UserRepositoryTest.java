package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {

    private UserRepository repository = new UserMemoryRepository();

    @Test
    @DisplayName("데이터를 저장한다.")
    void save() {
        User user = new User("forky", "1111", "hello@spring.com", "퐄퐄퐄");

        User searchedUser = repository.save(user);

        assertThat(user).isSameAs(searchedUser);
    }



}
