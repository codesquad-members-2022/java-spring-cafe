package com.kakao.cafe.service;

import java.util.Optional;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user){
        validateDuplicateEmail(user);
        validateDuplicateNickname(user);
        userRepository.save(user);
    }

    private void validateDuplicateEmail(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(m -> {
                throw new IllegalStateException(ErrorMessage.EXISTING_EMAIL.get());
            });
    }

    private void validateDuplicateNickname(User user) {
        userRepository.findByNickName(user.getNickname())
            .ifPresent(m -> {
                throw new IllegalStateException(ErrorMessage.EXISTING_NICKNAME.get());
            });
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }
}
