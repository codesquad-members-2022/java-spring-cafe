package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        validateUserId(user.getUserId());
        return userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateUserId(String userId) {
        userRepository.findByUserId(userId)
            .ifPresent((user) -> {
                throw new CustomException(ErrorCode.DUPLICATE_USER);
            });
    }

    public User updateUser(User user) {
        User findUser = findUser(user.getUserId());
        User updatedUser = findUser.update(user);

        return userRepository.save(updatedUser);
    }

}
