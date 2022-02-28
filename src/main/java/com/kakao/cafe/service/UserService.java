package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        checkDuplicateId(user);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void clearRepository() {
        userRepository.clear();
    }

    private void checkDuplicateId(User user) {
        userRepository.findById(user.getUserId()).ifPresent(u -> {
            throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        });
    }
}
