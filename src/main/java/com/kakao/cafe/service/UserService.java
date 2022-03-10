package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.DuplicateUserIdException;
import com.kakao.cafe.exception.NoMatchUserException;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final String DUPLICATE_USERID_MESSAGE = "이미 가입된 ID입니다. 다른 ID로 가입해주세요.";
    private final String NO_MATCH_USER_MESSAGE = "일치하는 회원이 없습니다. 확인 후 다시 시도해주세요.";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        if (!userRepository.findByUserId(user.getUserId()).isPresent()) {
            validateDuplicateUser(user);
        }
        return userRepository.save(user);
    }

    public Long nextUserSequence() {
        return userRepository.nextUserSequence();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(findUser -> {
                    throw new DuplicateUserIdException(DUPLICATE_USERID_MESSAGE);
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoMatchUserException(NO_MATCH_USER_MESSAGE));
    }

    public void deleteAllUsers() {
        userRepository.deleteAllUsers();
    }
}
