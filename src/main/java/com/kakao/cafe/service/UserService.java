package com.kakao.cafe.service;

import com.kakao.cafe.domain.UserInformation;
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

    public UserInformation update(String userId, UserInformation updatedUserInformation) {
        UserInformation userInformation = userRepository.findByUserId(userId).get();
        validatePassword(userInformation, updatedUserInformation.getPassword());

        return userRepository.save(updatedUserInformation);
    }

    public List<UserInformation> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserInformation> findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void deleteAll() {
        userRepository.clear();
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    private void validatePassword(UserInformation userInformation, String password) {
        if (userInformation.hasSamePassword(password) == false) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
