package com.kakao.cafe.integration;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSetUp {

    @Autowired
    private UserCollectionRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void rollback() {
        userRepository.deleteAll();
    }
}