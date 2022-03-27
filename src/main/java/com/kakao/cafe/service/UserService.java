package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.user.JdbcUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 회원입니다.")
        );
    }
}
