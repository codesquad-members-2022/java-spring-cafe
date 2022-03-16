package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.exception.UserIncorrectAccessException;
import com.kakao.cafe.exception.LoginFailedException;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
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

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId).ifPresent(m -> {
            throw new UserIncorrectAccessException("이미 존재하는 사용자입니다.");
        });
    }

    public User update(String userId, UserRequestDto userRequestDto) {
        User user = userRequestDto.convertToDomain();
        validateUser(userId, user.getPassword());
        return userRepository.save(user);
    }

    public void validateUser(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElse(null);
        if (user == null || !user.hasSamePassword(password)) {
            throw new LoginFailedException("아이디 또는 비밀번호가 틀립니다. 다시 로그인해주세요.");
        }
    }

    public void validateSessionOfUser(String id, HttpSession session) {
        Object sessionUser = session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new UserIncorrectAccessException("로그인이 정상적으로 되어있지 않습니다.");
        }

        if (!((UserResponseDto) sessionUser).hasSameUserId(id)) {
            throw new UserIncorrectAccessException("해당 계정의 정보는 수정할 수 없습니다.");
        }
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(user -> user.convertToDto()).collect(Collectors.toList());
    }

    public UserResponseDto findOne(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserIncorrectAccessException("해당되는 ID가 없습니다."));
        return user.convertToDto();
    }
}
