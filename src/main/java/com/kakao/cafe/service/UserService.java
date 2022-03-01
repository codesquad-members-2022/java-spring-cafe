package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.DuplicateUserIdException;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        validateDuplicateUser(user);
        return userRepository.join(user);
    }

    public Long nextUserSequence() {
        return userRepository.nextUserSequence();
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByUserId(user.getUserId());
        if (findUser != null) {
            throw new DuplicateUserIdException("이미 가입된 ID입니다. 새로운 ID로 가입해주세요.");
        }
    }
}
