package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @Test
    @DisplayName("회원 가입시 리포지토리에 저장이 올바로 되는지 확인한다.")
    public void save() {
        User user = new User();
        user.setUserId("1234");
        user.setName("spring");
        user.setPassword("111");
        user.setEmail("spring@c.com");

        repository.save(user);

        User result = repository.findById(user.getUserId()).get();
        assertThat(result).isEqualTo(user);

    }
}
