package com.kakao.cafe.service;

import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto signUp(User user) {
        UserResponseDto userResponseDto = user.of();
        validateDuplicatedUser(userResponseDto);
        userRepository.userSave(user);
        return userResponseDto;
    }

    public List<UserResponseDto> findUsers() {
        return userRepository.findAllUser()
                .stream()
                .map(User::of)
                .collect(Collectors.toList());
    }

    public UserResponseDto findIdUser(String userId) {
        return userRepository.findUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))
                .of();
    }

    public UserResponseDto findEmailUser(String userEmail) {
        return userRepository.findEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."))
                .of();
    }

    private void validateDuplicatedUser(UserResponseDto userResponseDto) {
        userRepository.findEmail(userResponseDto.getEmail()).ifPresent(s -> {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        });
    }
}
