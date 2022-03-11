package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginForm;
import com.kakao.cafe.dto.UserForm;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.util.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserForm userForm) {
        validateUserId(userForm.getUserId());
        return userRepository.save(Mapper.map(userForm, User.class));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateUserId(String userId) {
        userRepository.findByUserId(userId)
            .ifPresent((user) -> {
                throw new DuplicateException(ErrorCode.DUPLICATE_USER);
            });
    }

    public User updateUser(UserForm userForm) {
        User findUser = findUser(userForm.getUserId());
        User updatedUser = findUser.update(Mapper.map(userForm, User.class));

        return userRepository.save(updatedUser);
    }

    public User login(LoginForm loginForm) {
        User user = userRepository.findByUserId(loginForm.getUserId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return user.authenticate(loginForm);
    }
}
