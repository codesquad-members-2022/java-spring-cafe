package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {
    MemoryUserRepository repository;

    @BeforeEach
    public void beforeEach() {
         repository = new MemoryUserRepository();
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("새로 생성된 유저가 레포지토리에 잘 저장이 되는가")
    void save(){
        //given
        User user = new User("honux", "호눅스", "1234a", "honux77@gmail.com");

        //when
        repository.save(user);
        User result = repository.findById(user.getId()) // index 0부터 저장
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //then
        assertThat(user).isEqualTo(result);
    }

    @Test
    @DisplayName("유저 아이디를 통한 유저 검색이 잘 이루어지는가")
    void findByUserId(){
        //given
        User user1 = new User("honux", "호눅스", "1234a", "honux77@gmail.com");
        User user2 = new User("crong", "크롱", "5678@", "crong1004@naver.com");


        //when
        repository.save(user1);
        repository.save(user2);

        //then
        User result = repository.findByUserId("honux").get();
        assertThat(result).isEqualTo(user1);
    }

    @Test
    @DisplayName("레포지토리에 있는 모든 유저들이 잘 검색이 되는가")
    void findAll(){
        //given
        User user1 = new User("honux", "호눅스", "1234a", "honux77@gmail.com");
        User user2 = new User("crong", "크롱", "5678@", "crong1004@naver.com");
        User user3 = new User("ivy", "아이비", "1372345!@~", "ivy1234@kakao.com");

        //when
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);

        //then
        assertThat(repository.findAll()).contains(user1, user2, user3);
        assertThat(repository.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유저 수정이 잘 이루어지는가")
    void update() {
        //given
        User user = new User("honux", "호눅스", "1234a", "honux77@gmail.com");
        repository.save(user);

        //when
        User updateUser = new User("honux", "김나단", "호눅스짱짱!", "honux77@lucas.com");
        repository.update(updateUser, 0);

        //then
        User poppedUser = repository.findByUserId("honux").orElseThrow();
        assertThat("김나단").isEqualTo(poppedUser.getName());
        assertThat("호눅스짱짱!").isEqualTo(poppedUser.getPassword());
        assertThat("honux77@lucas.com").isEqualTo(poppedUser.getEmail());
    }
}
