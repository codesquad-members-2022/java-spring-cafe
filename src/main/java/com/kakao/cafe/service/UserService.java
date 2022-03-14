package com.kakao.cafe.service;

import com.kakao.cafe.Controller.dto.UserDto;
import com.kakao.cafe.Controller.dto.UserRequestDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.DuplicateUserIdException;
import com.kakao.cafe.exception.NoMatchUserException;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final String DUPLICATE_USERID_MESSAGE = "이미 가입된 ID입니다. 다른 ID로 가입해주세요.";
    private final String NO_MATCH_USER_MESSAGE = "일치하는 회원이 없습니다. 확인 후 다시 시도해주세요.";
    private final String NO_MATCH_PASSWORD_MESSAGE = "패스워드가 일치하지 않습니다.";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserRequestDto userRequestDto) {
        validateDuplicateUser(userRequestDto);

        return userRepository.save(UserRequestDto.toEntity(userRequestDto));
    }

    public User update(UserRequestDto userRequestDto) {
        User findUser = findByUserId(userRequestDto.getUserId());

        if (!findUser.isCorrectPassword(userRequestDto.getPassword())) {
            throw new IllegalArgumentException(NO_MATCH_PASSWORD_MESSAGE);
        }

        findUser.update(userRequestDto.getPassword(),
                userRequestDto.getName(),
                userRequestDto.getEmail());

        return userRepository.save(findUser);
   }

    private void validateDuplicateUser(UserRequestDto userRequestDto) {
        userRepository.findByUserId(userRequestDto.getUserId())
                .ifPresent(findUser -> {
                    throw new DuplicateUserIdException(DUPLICATE_USERID_MESSAGE);
                });
    }

    public List<UserDto> findUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    private User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoMatchUserException(NO_MATCH_USER_MESSAGE));
    }

    public UserRequestDto findUserRequestDto(String userId) {
        User findUser = findByUserId(userId);

        return UserRequestDto.from(findUser);
    }

    public UserDto findUserDto(String userId) {
        User findUser = findByUserId(userId);

        return UserDto.from(findUser);
    }
}
