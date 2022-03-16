package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.controller.dto.UserDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int signUp(SignUpRequestDto form) {
        validateDuplicatedUser(form);
        User newUser = form.toEntity();
        return userRepository.save(newUser);
    }

    private void validateDuplicatedUser(SignUpRequestDto form) {
        userRepository.findByEmail(form.getEmail())
            .ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 이메일입니다.");
            });
        userRepository.findByUserId(form.getUserId())
            .ifPresent(u -> {
                throw new IllegalStateException("이미 존재하는 닉네임입니다.");
            });
    }

    public UserDto findUser(String userId) {
        return userRepository.findByUserId(userId)
            .map(UserDto::new)
            .orElseThrow(() -> new NoSuchElementException("해당 닉네임을 가진 회원이 존재하지 않습니다."));
    }

    public List<UserDto> findUsers() {
        return userRepository.findAll().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }

    public int updateUser(UserUpdateRequestDto form) {
        User user = userRepository.findByUserId(form.getUserId())
            .orElseThrow(() -> new NoSuchElementException("해당 닉네임을 가진 회원이 존재하지 않습니다."));

        if (!user.isCorrectPassword(form.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User updatedUser = user.update(form.getNewEmail(), form.getNewPassword());

        return userRepository.save(updatedUser);
    }
}
