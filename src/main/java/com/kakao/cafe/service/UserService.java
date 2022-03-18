package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        userRepository.save(user);
        return user.getUserId();
    }

    public User findById(String id) throws IllegalStateException {
        return userRepository.findById(id).orElseThrow(()-> new IllegalStateException("유저를 찾을 수 없습니다."));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
