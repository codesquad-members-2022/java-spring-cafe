package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;
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

    public User join(UserRequestDto userRequestDto) {
        User user = userRequestDto.convertToDomain();
        validateDuplicateUser(user.getUserId());
        return userRepository.save(user);
    }

    public User update(String userId, UserRequestDto userRequestDto) {
        User user = userRequestDto.convertToDomain();
        validatePassword(userId, user.getPassword());
        return userRepository.save(user);
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(user -> user.convertToDto()).collect(Collectors.toList());
    }

    public UserResponseDto findOne(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("해당되는 ID가 없습니다."));
        return user.convertToDto();
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        });
    }

    private void validatePassword(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> {
            throw new IllegalStateException("해당되는 ID가 없습니다.");
        });

        if (!user.hasSamePassword(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }
}
