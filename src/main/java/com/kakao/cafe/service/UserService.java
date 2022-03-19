package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.user.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final MemoryUserRepository userRepository;

    @Autowired
    public UserService(MemoryUserRepository userRepository) {
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
