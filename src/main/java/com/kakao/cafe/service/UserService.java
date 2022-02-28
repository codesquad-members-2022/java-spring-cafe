package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(User user) {
        validateDuplicatedUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicatedUser(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }
}
