package com.kakao.cafe.integration.repository;


import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@ActiveProfiles(profiles = "local")
@Sql("classpath:/schema-h2.sql")
@Import(QueryProps.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("UserJdbcRepository JDBC 통합 테스트")
public class UserJdbcRepositoryTest {

    private final UserJdbcRepository userRepository;
    User user;

    @Autowired
    public UserJdbcRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate, QueryProps queryProps) {
        this.userRepository = new UserJdbcRepository(jdbcTemplate, queryProps);
    }

    @BeforeEach
    public void setUp() {
        user = User.createWithInput("userId", "userPassword", "userName", "user@example.com");
    }

    @Test
    @DisplayName("유저 객체를 저장소에 저장하고, 저장소에서 조회해 확인한다")
    public void savePersistTest() {
        // given
        User savedUser = userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUserId(savedUser.getUserId());

        // then
        then(findUser)
            .hasValueSatisfying(user -> {
                then(user.getUserId()).isEqualTo("userId");
                then(user.getPassword()).isEqualTo("userPassword");
                then(user.getName()).isEqualTo("userName");
                then(user.getEmail()).isEqualTo("user@example.com");
            });
    }

    @Test
    @DisplayName("업데이트된 유저 객체를 저장하고, 저장소에서 조회해 확인한다")
    public void saveMergeTest() {
        // given
        userRepository.save(user);

        User changedUser = User.createWithInput("userId", "userPassword", "otherName",
            "other@example.com");

        User updatedUser = userRepository.save(changedUser);

        // when
        Optional<User> findUser = userRepository.findByUserId(updatedUser.getUserId());

        // then
        then(findUser)
            .hasValueSatisfying(user -> {
                then(user.getUserId()).isEqualTo("userId");
                then(user.getPassword()).isEqualTo("userPassword");
                then(user.getName()).isEqualTo("otherName");
                then(user.getEmail()).isEqualTo("other@example.com");
            });
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // given
        userRepository.save(user);

        // when
        List<User> users = userRepository.findAll();

        // then
        then(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // given
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).hasValue(user);
    }

    @Test
    @DisplayName("존재하지 않는 유저아이디로 질문 객체를 조회한다")
    public void findNullTest() {
        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).isEqualTo(Optional.empty());
    }
}
