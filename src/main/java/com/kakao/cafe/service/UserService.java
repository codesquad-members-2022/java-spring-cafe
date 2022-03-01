package com.kakao.cafe.service;

import com.kakao.cafe.domain.UserInformation;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(MemoryUserRepository memoryUserRepository) {
        this.userRepository = memoryUserRepository;
    }

    public void join(UserInformation userInformation) {
        validateDuplicateUser(userInformation.getUserId());
        userRepository.savaUserInformation(userInformation);
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findUserInformationById(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    public List<UserInformation> findAllUsers() {
        return userRepository.findAllUserInformation();
    }
}
