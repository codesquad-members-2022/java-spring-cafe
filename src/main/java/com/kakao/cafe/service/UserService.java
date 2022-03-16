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

    public Long join(User user) {
        validateDuplicatedUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicatedUser(User user) {
        userRepository.findById(user.getUserId())
                .orElseThrow(() -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).get();
    }

}
