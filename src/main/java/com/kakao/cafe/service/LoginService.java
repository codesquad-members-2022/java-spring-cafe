package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.kakao.cafe.message.UserDomainMessage.*;

@Service
public class LoginService {

    private final CrudRepository<User, String> userRepository;

    public LoginService(CrudRepository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public User checkInfo(LoginParam loginParam) {
        String userId = loginParam.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(HttpStatus.OK, NO_SUCH_USER_MESSAGE));

        return user;
    }
}
