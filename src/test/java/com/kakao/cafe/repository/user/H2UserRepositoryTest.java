package com.kakao.cafe.repository.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.kakao.cafe.domain.user.User;

@ExtendWith(MockitoExtension.class)
class H2UserRepositoryTest {

    @InjectMocks
    H2UserRepository userRepository;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    private User user1;
    private User user2;

    @BeforeEach
    void init() {
        user1 = new User("lucid", "1111", "lee", "lee@lucid");
        user2 = new User("lucid2", "1234", "jo", "aa@lucid");
    }

    @DisplayName("존재하는 UserID를 전달하면 User 객체가 반환된다.")
    @Test
    void find_by_id() {
        // given
        given(jdbcTemplate.queryForObject(
            any(String.class),
            any(SqlParameterSource.class),
            any(RowMapper.class)
        )).willReturn(user1);

        // when
        Optional<User> optionalUser = userRepository.findById("lucid");

        // then
        User foundUser = optionalUser.orElseThrow();
        assertThat(foundUser).isEqualTo(user1);
    }

    @DisplayName("findAll을 통해 List<User>가 반환된다.")
    @Test
    void find_All() {
        // given
        given(jdbcTemplate.query(
            any(String.class),
            any(RowMapper.class)
        )).willReturn(List.of(user1, user2));

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }
}
