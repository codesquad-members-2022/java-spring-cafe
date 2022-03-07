package com.kakao.cafe.repository;

import com.kakao.cafe.controller.UserForm;
import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    UserRepository repository = new MemoryUserRepository();

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    User createUser(int number) {
        UserForm userForm = new UserForm("userId" + number,
                "userPassword" + number,
                "userName" + number,
                "userEmail" + number + "@naver.com");

        return new User(userForm);
    }

    @Test
    @DisplayName("유저를 저장하면, 저장소에 유저가 있어야 한다.")
    void save() {
        //given
        User user1 = createUser(1);

        //when
        repository.save(user1);

        //then
        User savedUser = repository.findById(user1.getUserId()).get();
        assertThat(user1).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("유저를 저장하면, 저장소에서 유저를 불러올 수 있다.")
    void findByName() {
        //given
        User user1 = createUser(1);
        User user2 = createUser(2);

        //when
        repository.save(user1);
        repository.save(user2);

        //then
        User foundUser = repository.findByName(user1.getName()).get();
        assertThat(user1).isEqualTo(foundUser);
    }

    @Test
    @DisplayName("저장소에 있는 모든 유저를 불러올 수 있다.")
    void findAll() {
        //given
        User user1 = createUser(1);
        User user2 = createUser(2);
        User user3 = createUser(3);

        //when
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);

        //then
        int userNumber = 3;
        List<User> allUsers = repository.findAll();
        assertThat(userNumber).isEqualTo(allUsers.size());
    }

    @Test
    @DisplayName("저장소에서 모든 유저를 지울 수 있다.")
    void deleteAll() {
        //given
        User user1 = createUser(1);
        User user2 = createUser(2);
        User user3 = createUser(3);

        //when
        repository.save(user1);
        repository.save(user2);
        repository.save(user3);

        //then
        int userNumber = 0;
        repository.deleteAll();
        List<User> emptyRepository = repository.findAll();
        assertThat(userNumber).isEqualTo(emptyRepository.size());
    }
}
