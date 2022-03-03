package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.UserMessage.*;

@Service
public class VolatilityUserService implements UserService {

    private final UserRepository userRepository;

    public VolatilityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.selectAll();
    }

    @Override
    public User addUser(User user) {
        validateDuplicateUser(user);
        userRepository.insertUser(user);
        return user;
    }
    private void validateDuplicateUser(User user) {
        userRepository.selectUser(user.getUserId())
                .ifPresent(id -> {
                    throw new DuplicateUserIdException(EXISTENT_ID_MESSAGE);
                });
    }

    @Override
    public User findUser(String id) {
        return userRepository.selectUser(id)
                .orElseThrow(() -> new NoSuchUserException(NON_EXISTENT_ID_MESSAGE));
    }
}
