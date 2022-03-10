package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User users) {
        validateDuplicateUser(users.getUserId());

        return userRepository.save(users);
    }

    public User update(String userId, User userUpdatedByUser) {
        User users = userRepository.findByUserId(userId);
        validatePassword(users, userUpdatedByUser.getPassword());

        return userRepository.save(userUpdatedByUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void deleteAll() {
        userRepository.clear();
    }

    private void validateDuplicateUser(String userId) {
        if (userRepository.findByUserId(userId) != null) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }
    }

    private void validatePassword(User users, String password) {
        if (!users.hasSamePassword(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
