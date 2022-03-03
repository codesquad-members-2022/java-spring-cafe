package com.kakao.cafe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user){
        validateDuplicateEmail(user);
        validateDuplicateNickname(user);
        userRepository.save(user);
    }

    private void validateDuplicateEmail(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(m -> {
                throw new IllegalStateException(ErrorMessage.EXISTING_EMAIL.get());
            });
    }

    private void validateDuplicateNickname(User user) {
        userRepository.findByNickname(user.getNickname())
            .ifPresent(m -> {
                throw new IllegalStateException(ErrorMessage.EXISTING_NICKNAME.get());
            });
    }

    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findUsers(){
        return new ArrayList<>(userRepository.findAll());
    }
}
