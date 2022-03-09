package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.UserMessage.*;

@Service
public class VolatilityUserService implements UserService {

    private final Repository<User, String> userRepository;

    public VolatilityUserService(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user)
                .orElseThrow(() -> new SaveUserException(ADD_FAIL_MESSAGE));
    }
    private void validateDuplicateUser(User user) {
        userRepository.findOne(user.getUserId())
                .ifPresent(id -> {
                    throw new DuplicateUserIdException(EXISTENT_ID_MESSAGE);
                });
    }

    @Override
    public User update(User user) {
        return userRepository.save(user)
                .orElseThrow(() -> new SaveUserException(UPDATE_FAIL_MESSAGE));
    }


    @Override
    public User search(String id) {
        return userRepository.findOne(id)
                .orElseThrow(() -> new NoSuchUserException(NON_EXISTENT_ID_MESSAGE));
    }
}
