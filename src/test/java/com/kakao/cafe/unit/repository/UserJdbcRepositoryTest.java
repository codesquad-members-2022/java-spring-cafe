package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class UserJdbcRepositoryTest {

    @InjectMocks
    UserJdbcRepository userRepository;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    QueryProps queryProps;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void savePersistTest() {
        // given
        given(queryProps.get(any()))
            .willReturn("");

        given(jdbcTemplate.queryForObject(any(String.class),
            any(BeanPropertySqlParameterSource.class), eq(Integer.class)))
            .willReturn(0);

        given(jdbcTemplate.update(any(String.class), any(BeanPropertySqlParameterSource.class)))
            .willReturn(1);

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getUserId()).isEqualTo("userId");
        assertThat(savedUser.getPassword()).isEqualTo("userPassword");
        assertThat(savedUser.getName()).isEqualTo("userName");
        assertThat(savedUser.getEmail()).isEqualTo("user@example.com");
    }
}
