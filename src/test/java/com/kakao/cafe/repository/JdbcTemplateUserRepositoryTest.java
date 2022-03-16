package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.User.UserBuilder;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class JdbcTemplateUserRepositoryTest {

    UserRepository userRepository;

    @Autowired
    public JdbcTemplateUserRepositoryTest(DataSource dataSource) {
        this.userRepository = new JdbcTemplateUserRepository(dataSource);
    }

    @Test
    @DisplayName("회원 아이디를 통해 회원을 검색할 수 있다.")
    void 회원_객체_조회_테스트() {
        // given
        String userId = "ID1";
        String password = "PW1";
        String name = "NAME1";
        String email = "1@1.com";

        // when
        User resultUser = userRepository.findById(userId).get();

        // then
        assertThat(resultUser.getUserId()).isEqualTo(userId);
        assertThat(resultUser.getPassword()).isEqualTo(password);
        assertThat(resultUser.getName()).isEqualTo(name);
        assertThat(resultUser.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("신규 회원 정보를 저장할 수 있다.")
    void 회원_정보_저장_테스트() {
        // given
        User newUser = new UserBuilder("ID3", "PW3")
            .setName("NAME3")
            .setEmail("3@3.com")
            .build();

        // when
        userRepository.save(newUser);

        // then
        User resultUser = userRepository.findById("ID3").get();
        assertThat(resultUser.getUserId()).isEqualTo("ID3");
        assertThat(resultUser.getPassword()).isEqualTo("PW3");
        assertThat(resultUser.getName()).isEqualTo("NAME3");
        assertThat(resultUser.getEmail()).isEqualTo("3@3.com");
    }

    @Test
    @DisplayName("회원 정보를 수정할 수 있다.")
    void 회원_정보_수정_테스트() {
        // given
        User oldUser = new UserBuilder("ID2", "PW2")
            .setName("NAME2")
            .setEmail("2@2.com")
            .build();
        User newUser = new UserBuilder("ID2", "pw2")
            .setName("name2")
            .setEmail("2@2.net")
            .build();

        // when
        userRepository.update(oldUser, newUser);

        // then
        User resultUser = userRepository.findById("ID2").get();
        assertThat(resultUser.getUserId()).isEqualTo("ID2");
        assertThat(resultUser.getPassword()).isEqualTo("pw2");
        assertThat(resultUser.getName()).isEqualTo("name2");
        assertThat(resultUser.getEmail()).isEqualTo("2@2.net");
    }

    @Test
    @DisplayName("모든 회원을 조회할 수 있다.")
    void 모든_회원_조회_테스트() {
        // given
        String userId1 = "ID1";
        String userId2 = "ID2";

        // when
        List<User> resultUserList = userRepository.findAll();

        // then
        assertThat(resultUserList).hasSize(2);
        assertThat(resultUserList.get(0).getUserId()).isEqualTo(userId1);
        assertThat(resultUserList.get(1).getUserId()).isEqualTo(userId2);
    }
}
