package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.login.dto.LoginDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(m -> m.isSamePassword(password))
                .orElse(null);
    }

    public User login(LoginDto dto) {
        return login(dto.getUserId(), dto.getPassword());
    }

    public boolean logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
            return true;
        }
        return false;
    }
}
