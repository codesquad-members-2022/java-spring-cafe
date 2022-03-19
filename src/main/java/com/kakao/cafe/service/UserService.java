package com.kakao.cafe.service;

import com.kakao.cafe.controller.SignUpUserDto;
import com.kakao.cafe.controller.UpdateUserDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int signUp(SignUpUserDto signUpDto) {
        validateDuplicateUser(signUpDto.getUserId());
        validateDuplicateEmail(signUpDto.getEmail());

        return userRepository.save(signUpDto.toEntity());
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(int id) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    public int update(UpdateUserDto updateUserData) {
        validateDuplicateEmail(updateUserData.getEmail());

        User updateTargetUser = findOne(updateUserData.getId());
        updateTargetUser.changeEmail(updateUserData.getEmail());
        updateTargetUser.changeName(updateUserData.getName());
        updateTargetUser.changePassword(updateUserData.getPassword());

        return userRepository.save(updateTargetUser);
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId)
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    private void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new IllegalStateException("이미 사용중인 이메일입니다.");
            });
    }

}
