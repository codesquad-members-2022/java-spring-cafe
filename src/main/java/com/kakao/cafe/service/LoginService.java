package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.LoginForm;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginForm login(String userId, String password){
        User user = getUser(userId);
        return checkPassword(userId, password, user);
    }

    private LoginForm checkPassword(String userId, String password, User user) {
        if (user.getPassword().equals(password)) {
            LoginForm loginForm = new LoginForm(userId, password);
            loginForm.setId(user.getId());
            return loginForm;
        }
        throw new IllegalArgumentException("비밀번호가 틀립니다.");
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다."));
    }


}
