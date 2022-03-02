package com.kakao.cafe.service;

import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.dto.UserListDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRegisterFormDto userSignUpFormDto) {
        validateDuplicateUserId(userSignUpFormDto);
        userRepository.save(userSignUpFormDto.toEntity());
    }

    public void validateDuplicateUserId(UserRegisterFormDto userSignUpFormDto) {
        userRepository.findById(userSignUpFormDto.getUserId())
            .ifPresent(user -> {
                throw new IllegalStateException("동일한 ID를 가지는 회원이 이미 존재합니다.");
            });
    }

    public List<UserListDto> showAllUsers() {
        return userRepository.findAll().stream()
            .map(UserListDto::new)
            .collect(Collectors.toList());
    }
}
