package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입
     */
    public String join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 유저 리스트
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 찾기
     */
    public User findOne(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return validateNoneUser(optionalUser);
    }

    private User validateNoneUser(Optional<User> optionalUser) {
        return optionalUser.orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        });
    }

}
