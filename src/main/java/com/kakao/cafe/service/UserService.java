package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.UserJoinRequestDto;
import com.kakao.cafe.controller.dto.UserLoginRequestDto;
import com.kakao.cafe.controller.dto.UserUpdateRequestDto;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private static final String NOT_FOUND_USER_MESSAGE = "해당 회원이 존재하지 않습니다.";
    private static final String DUPLICATED_USER_MESSAGE = "이미 존재하는 회원입니다.";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinRequestDto requestDto) {
        User user = requestDto.toEntity();
        validateDuplicatedUserId(user);
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER_MESSAGE));
    }

    private void validateDuplicatedUserId(User user) {
        userRepository.findByUserId(user.getUserId()).ifPresent(m -> {
            throw new IllegalStateException(DUPLICATED_USER_MESSAGE);
        });
    }

    public void update(UserUpdateRequestDto dto) {
        User user = findByUserId(dto.getUserId());
        if (!user.isMatchPassword(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        User updateUser = user.update(dto.toEntity());
        userRepository.save(updateUser);
    }

    public User login(UserLoginRequestDto dto) {
        String userId = dto.getUserId();
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            return null;
        }
        User user1 = user.get();
        return (user1.isMatchPassword(dto.getPassword())) ? user1 : null;
    }
}
