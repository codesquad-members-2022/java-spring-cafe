package com.kakao.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateUniqueNickname(user);
        validateUniqueEmail(user);
        userRepository.save(user);
    }

    public void update(User user, User updatedUser) {
        if (!user.matchesNickname(updatedUser.getNickname())) { // 기존 닉네임과 같을 경우 validation 패스
            validateUniqueNickname(updatedUser);
        }
        if (!user.matchesEmail(updatedUser.getEmail())) {
            validateUniqueEmail(updatedUser);
        }
        userRepository.update(user, updatedUser);
    }

    private void validateUniqueNickname(User user) {
        userRepository.findByNickname(user.getNickname()).ifPresent(m -> {
            throw new IllegalArgumentException(ErrorMessage.EXISTING_NICKNAME.get());
        });
    }

    private void validateUniqueEmail(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new IllegalArgumentException(ErrorMessage.EXISTING_EMAIL.get());
        });
    }

    public User findById(int id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_MATCH_USER.get()));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_MATCH_USER.get()));
    }

    public List<User> findUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

}
