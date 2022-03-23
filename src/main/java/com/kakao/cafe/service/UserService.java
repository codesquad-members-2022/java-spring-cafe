package com.kakao.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.UserException;
import com.kakao.cafe.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int join(User user) {
        validateUniqueNickname(user);
        validateUniqueEmail(user);
        user.checkBlankInput();
        return userRepository.save(user);
    }

    public void update(User user, User updatedUser) {
        validateUpdatedInput(user, updatedUser);
        user.updateProfile(updatedUser.getNickname(), updatedUser.getEmail());
        userRepository.update(user);
    }

    private void validateUniqueNickname(User user) {
        userRepository.findByNickname(user.getNickname()).ifPresent(m -> {
            throw new UserException(ErrorCode.EXISTING_NICKNAME);
        });
    }

    private void validateUniqueEmail(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
            throw new UserException(ErrorCode.EXISTING_EMAIL);
        });
    }

    private void validateUpdatedInput(User user, User updatedUser) {
        if (!user.getPassword().equals(updatedUser.getPassword())) {
            throw new UserException(ErrorCode.WRONG_PASSWORD);
        }
        if (!user.matchesNickname(updatedUser.getNickname())) { // 기존 닉네임과 같을 경우 validation 패스
            validateUniqueNickname(updatedUser);
        }
        if (!user.matchesEmail(updatedUser.getEmail())) {
            validateUniqueEmail(updatedUser);
        }
    }

    public User findById(int id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserException(ErrorCode.NO_MATCH_USER));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new UserException(ErrorCode.NO_MATCH_USER));
    }

    public List<User> findUsers() {
        return new ArrayList<>(userRepository.findAll());
    }
}
