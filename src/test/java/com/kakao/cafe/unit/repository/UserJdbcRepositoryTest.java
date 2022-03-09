package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.jdbc.UserJdbcRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

        given(queryProps.get(any()))
            .willReturn("");
    }

    @Test
    @DisplayName("유저 객체를 저장한다")
    public void savePersistTest() {
        // given
        given(jdbcTemplate.queryForObject(any(String.class),
            any(BeanPropertySqlParameterSource.class), eq(Integer.class)))
            .willReturn(0);

        given(jdbcTemplate.update(any(String.class), any(BeanPropertySqlParameterSource.class)))
            .willReturn(1);

        // when
        User savedUser = userRepository.save(user);

        // then
        then(savedUser.getUserId()).isEqualTo("userId");
        then(savedUser.getPassword()).isEqualTo("userPassword");
        then(savedUser.getName()).isEqualTo("userName");
        then(savedUser.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("변경된 유저 객체를 저장한다")
    public void saveMergeTest() {
        // given
        User changedUser = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("otherName")
            .email("other@example.com")
            .build();

        given(jdbcTemplate.queryForObject(any(String.class),
            any(BeanPropertySqlParameterSource.class), eq(Integer.class)))
            .willReturn(1);

        given(jdbcTemplate.update(any(String.class), any(BeanPropertySqlParameterSource.class)))
            .willReturn(1);

        // when
        User savedUser = userRepository.save(changedUser);

        // then
        then(savedUser.getUserId()).isEqualTo("userId");
        then(savedUser.getPassword()).isEqualTo("userPassword");
        then(savedUser.getName()).isEqualTo("otherName");
        then(savedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("모든 유저 객체를 조회한다")
    public void findAllTest() {
        // given
        given(jdbcTemplate.query(any(String.class), any(RowMapper.class)))
            .willReturn(List.of(user));

        // when
        List<User> users = userRepository.findAll();

        // then
        then(users).containsExactly(user);
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저 객체를 조회한다")
    public void findByUserIdTest() {
        // given
        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(
            RowMapper.class)))
            .willReturn(user);

        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).hasValue(user);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 질문 객체를 조회한다")
    public void findNullTest() {
        // given
        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(
            RowMapper.class)))
            .willThrow(EmptyResultDataAccessException.class);

        // when
        Optional<User> findUser = userRepository.findByUserId(user.getUserId());

        // then
        then(findUser).isEqualTo(Optional.empty());
    }
}
