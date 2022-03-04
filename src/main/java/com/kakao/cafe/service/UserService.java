package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        userRepository.save(user);
        return user.getUserId();
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
