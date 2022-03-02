package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. 회원 가입 기능
    // TODO 중복 회원 체크 후 가입 시작하도록 로직을 추가해야 한다.
    public void createUser(User user) {
        userRepository.createUser(user);
    }

    // 2. 회원 조회 기능
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    // 3. 회원 프로필 조회 기능
    public Optional<User> findUser(String userName) {
        return userRepository.findUser(userName);
    }
}
