package com.kakao.cafe.domain.user;

import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest//transaction 포함
@Sql({"/schema.sql", "/test_data.sql"})
class UserRepositoryTest {
    Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    DataSource dataSource;

    JdbcUserRepository repository;


    @BeforeEach
    void setUp() {
        repository = new JdbcUserRepository(dataSource);
    }


    @Test
    @DisplayName("jdbc save")
    void save() {
        //given
        User user = new User("save", "save", "save", "save");
        repository.save(user);
        //when
        User saveUser = repository.findByUserId("save");
        //then
        assertThat(saveUser.getId()).isEqualTo(4L);
        assertThat(saveUser.getUserId()).isEqualTo("save");
        assertThat(saveUser.getName()).isEqualTo("save");
        assertThat(saveUser.getEmail()).isEqualTo("save");
    }

    @Test
    @DisplayName("jdbc findById")
    void findById() {
        //given
        log.info("test_data.sql 파일에 테스트 데이터 생성");
        //when
        User userA = repository.findById(1L);
        User userB = repository.findById(2L);
        User userC = repository.findById(3L);
        //then
        assertThat(userA.getUserId()).isEqualTo("test1");
        assertThat(userB.getUserId()).isEqualTo("test2");
        assertThat(userC.getUserId()).isEqualTo("test3");
    }
    @Test
    @DisplayName("jdbc findByUserId")
    void findByUserId() {
        //when 실행
        User userA = repository.findByUserId("test1");
        User userB = repository.findByUserId("test2");
        User userC = repository.findByUserId("test3");
        //then 검증
        assertThat(userA.getName()).isEqualTo("name1");
        assertThat(userB.getName()).isEqualTo("name2");
        assertThat(userC.getName()).isEqualTo("name3");
    }
    @Test
    @DisplayName("jdbc findAll")
    void findAll() {
        //when 실행
        List<User> userList = repository.findAll();
        //then 검증
        assertThat(userList.size()).isEqualTo(3);
    }
    @Test
    @DisplayName("jdbc update")
    void update() {
        //given 준비
        User user = repository.findByUserId("test1");
        UserUpdateDto dto = new UserUpdateDto("updateName", "updateEmail");
        user.updateProfile(dto);
        //when 실행
        repository.updateUser(user.getId(), user);
        User updateUser = repository.findByUserId("test1");
        //then 검증
        assertThat(updateUser.getName()).isEqualTo("updateName");
    }
}
