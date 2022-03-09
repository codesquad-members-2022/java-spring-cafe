package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private MemoryUserRepository memoryUserRepository;

    public UserService(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    public String join(User user) {
        memoryUserRepository.save(user);
        return user.getName();
    }

    public List<User> findUsers() {
        return memoryUserRepository.findAll();
    }
}
