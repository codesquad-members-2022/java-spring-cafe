package com.kakao.cafe.service;

import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signUp(User user) {
        validateDuplicatedUser(user);
        userRepository.userSave(user);
        return user.getUserId();
    }

    public List<User> findUsers() {
        return userRepository.findAllUser();
    }

    public User findIdUser(String userId) {
        return userRepository.findUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    public User findEmailUser(String userEmail) {
        return userRepository.findEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private void validateDuplicatedUser(User user) {
        userRepository.findEmail(user.getEmail()).ifPresent(s -> {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        });
    }
}
