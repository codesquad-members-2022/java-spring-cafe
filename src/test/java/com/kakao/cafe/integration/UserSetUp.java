package com.kakao.cafe.integration;

import com.kakao.cafe.User;
import com.kakao.cafe.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSetUp {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void rollback() {
        userRepository.setUserNum(0);
        userRepository.deleteAll();
    }
}