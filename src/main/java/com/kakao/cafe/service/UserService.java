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

    public User join(User users) {
        validateDuplicateUser(users.getUserId());

        return userRepository.save(users);
    }

    public User update(String userId, User updatedUsers) {
        User users = userRepository.findByUserId(userId).get();
        validatePassword(users, updatedUsers.getPassword());

        return userRepository.save(updatedUsers);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void deleteAll() {
        userRepository.clear();
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    private void validatePassword(User users, String password) {
        if (users.hasSamePassword(password) == false) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
