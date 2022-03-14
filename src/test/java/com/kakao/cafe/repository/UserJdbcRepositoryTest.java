package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Transactional
class UserJdbcRepositoryTest {

    private UserJdbcRepository userJdbcRepository;
    private User user;

    @Autowired
    public UserJdbcRepositoryTest(DataSource dataSource) {
        this.userJdbcRepository = new UserJdbcRepository(dataSource);
    }

    @BeforeEach
    void setUp() {
        user = new User("ader", "1234", "park", "asdf@gmail.com", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("정상적인 User로 가입을 시도하면 정상적으로 회원가입이 되며 저장된 User를 리턴한다")
    void saveTest() {
        // given

        // when
        User saveUser = userJdbcRepository.save(user);

        // then
        assertThat(saveUser.isCorrectUser(user.getUserId())).isTrue();
    }

    @Test
    @DisplayName("이미 회원가입된 userId로 다시 save를 시도하면 변경된 값으로 update되며 변경된 User를 리턴한다")
    void updateTest() {
        // given
        userJdbcRepository.save(user);
        User updateUser = new User("ader", "1234", "kim", "asdf22@gmail.com", LocalDateTime.now(), LocalDateTime.now());

        // when
        User updatedUser = userJdbcRepository.save(updateUser);

        // then
        User findUser = userJdbcRepository.findByUserId("ader").orElse(null);
        assertThat(findUser.getName()).isEqualTo(updatedUser.getName());
    }

    @Test
    @DisplayName("저장되어 있는 userId로 검색을 시도하면 해당하는 User를 리턴한다")
    void findByUserIdTest() {
        // given
        User saveUser = userJdbcRepository.save(user);

        // when
        User findUser = userJdbcRepository.findByUserId(saveUser.getUserId()).orElse(null);

        // then
        assertThat(findUser.isCorrectUser(saveUser.getUserId())).isTrue();
    }

    @Test
    @DisplayName("findAll 메소드를 호출하면 저장되어있는 User 리스트를 리턴한다")
    void findAllTest() {
        // given
        User user2 = new User("ader2", "1234", "kim2", "asdf2@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        User user3 = new User("ader3", "1234", "kim3", "asdf3@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        userJdbcRepository.save(user);
        userJdbcRepository.save(user2);
        userJdbcRepository.save(user3);

        // when
        List<User> users = userJdbcRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(3);
    }
}
