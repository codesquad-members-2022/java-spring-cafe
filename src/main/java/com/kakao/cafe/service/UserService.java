package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.JdbcUserRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(JdbcUserRepository jdbcUserRepository) {

        this.userRepository = jdbcUserRepository;
    }

    public void join(User user) {
        if (findOne(user.getUserId())!=null){
            throw new RuntimeException();
        }
        userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.getUserList();
    }

    public User findOne(String userId) {
        return userRepository.findById(userId);
    }
}
