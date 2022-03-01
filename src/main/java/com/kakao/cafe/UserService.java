package com.kakao.cafe;

import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        validateUserId(user.getUserId());
        return userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void validateUserId(String userId) {
        userRepository.findByUserId(userId)
            .ifPresent((user) -> {
                throw new CustomException(ErrorCode.DUPLICATE_USER);
            });
    }

}
