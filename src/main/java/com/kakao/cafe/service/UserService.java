package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateUser(user);
        userRepository.save(user);
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    private void validateUser(User user) {
        validateUserId(user);
        validateEmail(user);
    }

    private void validateUserId(User user) {
        boolean isExistUserId = userRepository.isExistUserId(user.getUserId());
        if (isExistUserId) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }
    }

    private void validateEmail(User user) {
        boolean isExistEmail = userRepository.isExistEmail(user.getEmail());
        if (isExistEmail) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
    }
}
