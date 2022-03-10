package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.SignUpRequestDto;
import com.kakao.cafe.controller.dto.UserDto;
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
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    public UserDto findUser(String email) {
        return userRepository.findByEmail(email)
            .map(UserDto::new)
            .orElseThrow(() -> new NoSuchElementException("해당 이메일을 가진 회원이 존재하지 않습니다."));
    }

    public List<UserDto> findUsers() {
        return userRepository.findAll().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }
}
