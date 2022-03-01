package com.kakao.cafe;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        validateUserId(user.getUserId());
        return userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 아이디입니다."));
    }

    public void validateUserId(String userId) {
        userRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("이미 등록된 유저 아이디입니다."));
    }

}
