package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Long join(User user) {
        validateDuplicateUserId(user);
        validateDuplicateEmail(user);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    private void validateDuplicateUserId(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    private void validateDuplicateEmail(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }

    @Override
    public Optional<User> findOneUser(Long id) {
        return userRepository.findById(id);
    }
}
