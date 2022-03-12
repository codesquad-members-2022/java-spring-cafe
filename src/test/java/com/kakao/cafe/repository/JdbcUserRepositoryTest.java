package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@DisplayName("JdbcUserRepository 단위 테스트")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JdbcUserRepositoryTest {

    private final JdbcUserRepository jdbcUserRepository;

    @Autowired
    public JdbcUserRepositoryTest(DataSource dataSource) {
        jdbcUserRepository = new JdbcUserRepository(dataSource);
    }

    private User user;

    @BeforeEach
    void setup() {
        user = new User("ikjo", "1234", "조명익", "auddlr100@naver.com");
    }

    @DisplayName("주어진 user 객체의 사용자 정보 데이터를 저장한다.")
    @Test
    void 사용자_정보_저장() {
        // when
        jdbcUserRepository.save(user);

        // then
        User result = jdbcUserRepository.findByUserId("ikjo").orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getName()).isEqualTo("조명익");
        assertThat(result.getEmail()).isEqualTo("auddlr100@naver.com");
    }

    @DisplayName("특정 사용자 ID로 해당 사용자 정보 데이터를 조회한다.")
    @Test
    void 특정_사용자_정보_조회() {
        // given
        jdbcUserRepository.save(user);

        // when
        User result = jdbcUserRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));

        // then
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getName()).isEqualTo("조명익");
        assertThat(result.getEmail()).isEqualTo("auddlr100@naver.com");
    }

    @DisplayName("저장된 사용자 정보 2개를 모두 조회한다.")
    @Test
    void 모든_사용자_정보_조회() {
        // given
        jdbcUserRepository.clear();
        jdbcUserRepository.save(user);
        jdbcUserRepository.save(new User("ikjo93", "1234", "조명익", "auddlr100@naver.com"));

        // when
        List<User> users = jdbcUserRepository.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

    @DisplayName("기존 사용자 정보를 수정한다.")
    @Test
    void 사용자_정보_수정() {
        // given
        jdbcUserRepository.save(user);
        jdbcUserRepository.save(new User("ikjo", "1234", "익조", "auddlr100@naver.com"));

        // when
        User result = jdbcUserRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));

        // then
        assertThat(result.getName()).isEqualTo("익조");
    }
}
