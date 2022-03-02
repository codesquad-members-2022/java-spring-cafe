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
        return userRepository.savaUserInformation(userInformation);
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findUserInformationById(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    public List<UserInformation> findAllUsers() {
        return userRepository.findAllUserInformation();
    }

    public Optional<UserInformation> findOneUser(String userId) {
        return userRepository.findUserInformationById(userId);
    }

    public void deleteAllUsers() {
        userRepository.clearUserInformationList();
    }
}
