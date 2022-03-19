package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.exception.LoginFailedException;
import com.kakao.cafe.exception.UserIncorrectAccessException;
import com.kakao.cafe.repository.UserRepository;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUser(String userId, String password) {
        User user = userRepository.findByUserId(userId).orElse(null);
        if (user == null || !user.hasSamePassword(password)) {
            throw new LoginFailedException("아이디 또는 비밀번호가 틀립니다. 다시 로그인해주세요.");
        }
    }

    public void validateUserIdOfSession(String userId, UserResponseDto userResponseDto) {
        validateSession(userResponseDto);

        if (!userResponseDto.hasSameUserId(userId)) {
            throw new UserIncorrectAccessException("다른 사용자의 게시글을 변경할 수 없습니다. 로그인 상태를 확인해주세요.");
        }
    }

    private void validateSession(UserResponseDto userResponseDto) {
        if (userResponseDto == null) {
            throw new UserIncorrectAccessException("로그인이 정상적으로 되어있지 않습니다.");
        }
    }
}
