package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password) {
        return userRepository.findById(userId)
                .filter(m -> m.isSamePassword(password))
                .orElse(null);
    }

    public boolean logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
            return true;
        }
        return false;
    }
}
