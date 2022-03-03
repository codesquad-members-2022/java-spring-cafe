package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(User user) {
        validateDuplicatedUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicatedUser(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 회원이 존재하지 않습니다."));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
