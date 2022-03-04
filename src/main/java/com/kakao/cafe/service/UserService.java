package com.kakao.cafe.service;

import com.kakao.cafe.dto.UserInformation;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInformation join(UserInformation userInformation) {
        validateDuplicateUser(userInformation.getUserId());
        return userRepository.save(userInformation);
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    public List<UserInformation> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserInformation> findOneUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void deleteAllUsers() {
        userRepository.clear();
    }
}
