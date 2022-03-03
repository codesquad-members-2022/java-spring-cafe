package com.kakao.cafe.service;

import java.util.Optional;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByNickName(user.getNickname())
            .ifPresent(m -> {
                throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
            });
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }
}
