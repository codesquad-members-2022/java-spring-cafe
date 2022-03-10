package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.findUser(user.getUserName())
                .ifPresent(foundUser -> {
                    throw new IllegalStateException("중복으로 가입할 수 없습니다.");
                });

        userRepository.createUser(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User findUser(String userName) {
        return userRepository.findUser(userName)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 유저입니다."));
    }
}
