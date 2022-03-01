package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.repository.MemoryUserRepository;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository = new MemoryUserRepository();

    //회원가입
    public Long join(User user) {
        //optional getOrElse를 알아볼 것.
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    //전체 회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

}
