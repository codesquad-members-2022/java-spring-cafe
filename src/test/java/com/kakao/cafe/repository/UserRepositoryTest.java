package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Autowired UserRepository userRepository = new MemoryUserRepository();

    @AfterEach
    void afterEach(){
        userRepository.clearStorage();
    }


    @Test
    void save() {
        //given
        User user = new User("zzangmin", "1234", "리뷰어짱", "zzangmin@gmail.com");
        //when
        userRepository.save(user);
        //then
        assertThat(user).isEqualTo(userRepository.findById("zzangmin"));
    }

    @Test
    void getUserList() {
        //given
        User user = new User("zzangmin", "1234", "리뷰어짱", "zzangmin@gmail.com");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        //when
        userRepository.save(user);
        //then
        assertThat(userList).isEqualTo(userRepository.getUserList());

    }

    @Test
    void findById() {
        //given
        User user = new User("zzangmin", "1234", "리뷰어짱", "zzangmin@gmail.com");
        //when
        userRepository.save(user);
        //then
        assertNotNull(userRepository.findById("zzangmin"));
    }
}