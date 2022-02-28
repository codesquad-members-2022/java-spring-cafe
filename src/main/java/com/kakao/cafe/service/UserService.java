package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public void join(User user) {
        userRepository.save(user);
    }

    public User findOne(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
