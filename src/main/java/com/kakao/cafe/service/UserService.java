package com.kakao.cafe.service;

import java.util.ArrayList;
import java.util.List;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateDuplicateNickname(user);
        userRepository.save(user);
    }

    private void validateDuplicateNickname(User user) {
        userRepository.findByNickname(user.getNickname())
            .ifPresent(m -> {
                throw new IllegalStateException(ErrorMessage.EXISTING_NICKNAME.get());
            });
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new IllegalStateException(ErrorMessage.NO_MATCH.get())
        );
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() ->
            new IllegalStateException(ErrorMessage.NO_MATCH.get())
        );
    }

    public List<User> findUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
