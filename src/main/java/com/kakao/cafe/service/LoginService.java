package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.repository.DomainRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final DomainRepository<User, String> userRepository;

    public LoginService(DomainRepository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public User checkInfo(LoginParam loginParam) {
        String userId = loginParam.getUserId();
        User user = userRepository.findOne(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        return user;
    }
}
