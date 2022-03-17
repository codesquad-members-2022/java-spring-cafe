package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class H2UserRepositoryTest {

    H2UserRepository h2UserRepository;

    @Autowired
    public H2UserRepositoryTest(DataSource dataSource) {
        this.h2UserRepository = new H2UserRepository(dataSource);
    }

    @Test
    @DisplayName("회원을 저장하고, 저장된 회원의 id를 리턴한다.")
    void save() {
        //given
        User validUser = new User("new@test.user", "new user", "password");

        //when
        int savedUserIndex = h2UserRepository.save(validUser);

        //then
        assertThat(savedUserIndex).isEqualTo(4);
    }

    @Test
    @DisplayName("회원을 존재하지 않는 이메일으로 조회하면, 해당 이메일을 가진 회원을 리턴한다.")
    void findByEmail_invalidEmail() {
        //given

        //when
        Optional<User> user = h2UserRepository.findByEmail("no@user.com");

        //then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("회원을 존재하는 이메일으로 조회하면, 해당 이메일을 가진 회원을 리턴한다.")
    void findByEmail_validEmail() {
        //given

        //when
        Optional<User> user = h2UserRepository.findByEmail("test1@user.com");

        //then
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getEmail()).isEqualTo("test1@user.com");
    }

    @Test
    @DisplayName("회원을 존재하지 않는 닉네임으로 조회하면, 해당 닉네임를 가진 회원을 리턴한다.")
    void findByUserId_invalidUserId() {
        //given

        //when
        Optional<User> user = h2UserRepository.findByUserId("TEST100");

        //then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("회원을 존재하는 닉네임으로 조회하면, 해당 닉네임를 가진 회원을 리턴한다.")
    void findByUserId_validUserId() {
        //given

        //when
        Optional<User> user = h2UserRepository.findByUserId("TEST1");

        //then
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().hasSameUserId("TEST1"));
    }

    @Test
    @DisplayName("회원을 전부 조회하면, 저장된 회원 전부를 List에 담아 리턴한다.")
    void findAll() {
        //given

        //when
        List<User> users = h2UserRepository.findAll();

        //then
        assertThat(users.size()).isEqualTo(3);
    }
}
