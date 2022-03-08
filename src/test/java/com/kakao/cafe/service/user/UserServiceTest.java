package com.kakao.cafe.service.user;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserForm;
import com.kakao.cafe.repository.user.UserMemoryRepository;
import com.kakao.cafe.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    UserRepository repository = new UserMemoryRepository();
    UserService service = new UserServiceImpl(repository);

    @BeforeEach
    void clear() {
        repository.clear();
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        UserForm form = new UserForm("forky", "1111", "hello@spring.com", "퐄퐄퐄");
        service.createUser(form);

        User user = service.findSingleUser(1L).get();

        assertThat(user.getUserId()).isEqualTo("forky");
        assertThat(user.getName()).isEqualTo("퐄퐄퐄");

    }
}
