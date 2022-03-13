package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.domain.User;
import com.ttasjwi.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        userRepository.save(user);
        return user.getUserName();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException("그런 회원은 존재하지 않습니다."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
