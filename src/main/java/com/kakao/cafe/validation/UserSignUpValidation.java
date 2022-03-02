package com.kakao.cafe.validation;

import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.UserRepository;

public class UserSignUpValidation {

    private UserSignUpValidation() {

    }

    public static void validateDuplicateUserEmail(UserRepository userRepository, User user) {
        userRepository.findEmail(user.getEmail())
                .ifPresent(s -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }
}
