package com.kakao.cafe.user.service;

import com.kakao.cafe.user.model.User;
import com.kakao.cafe.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> {throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");});
    }
}
