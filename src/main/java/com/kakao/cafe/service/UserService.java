package com.kakao.cafe.service;

import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.validation.UserSignUpValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(User user) {
        UserSignUpValidation.validateDuplicateUserEmail(userRepository, user);
        userRepository.userSave(user);
        log.info("가입 성공: {}", user.getEmail());
        return user.getId();
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
}
