package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.dto.UserListDto;
import com.kakao.cafe.web.dto.UserProfileDto;
import com.kakao.cafe.web.dto.UserRegisterFormDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRegisterFormDto userRegisterFormDto) {
        String userId = userRegisterFormDto.getUserId();
        validateDuplicateUserId(userId);
        userRepository.save(userRegisterFormDto.toEntity());
    }

    private void validateDuplicateUserId(String userId) {
        userRepository.findById(userId)
            .ifPresent(user -> {
                throw new IllegalStateException("동일한 ID를 가지는 회원이 이미 존재합니다.");
            });
    }

    public List<UserListDto> showAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
            .map(user -> {
                UserListDto userListDto = new UserListDto(user);
                userListDto.setUserNum(userList.indexOf(user));
                return userListDto;
            })
            .collect(Collectors.toList());
    }

    public UserProfileDto showOne(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return new UserProfileDto(validateExistenceUserId(optionalUser));
    }

    private User validateExistenceUserId(Optional<User> optionalUser) {
        return optionalUser.orElseThrow(() -> {
            throw new IllegalStateException("해당 ID를 가지는 회원이 존재하지 않습니다.");
        });
    }
}
