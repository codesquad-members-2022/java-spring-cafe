package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public Long signUp(UserDto user) {
        validateDuplicateUser(user);
        userRepository.save(new User(user));
        return user.getId();
    }

    private void validateDuplicateUser(UserDto user) {
        userRepository.findByUserId(user.getUserId())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    //전체 회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long id) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

}
