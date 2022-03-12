package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.UserMessage.*;

@Service
public class UserService {

    private final Repository<User, String> userRepository;

    public UserService(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User add(NewUserParam newUserParam) {
        User user = newUserParam.convertToUser();
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

    public User update(ModifiedUserParam modifiedUserParam) {
        modifiedUserParam.isValidRequest();
        return userRepository.save(modifiedUserParam.convertToUser())
                .orElseThrow(() -> new SaveUserException(UPDATE_FAIL_MESSAGE));
    }

    public User search(String id) {
        return userRepository.findOne(id)
                .orElseThrow(() -> new NoSuchUserException(NON_EXISTENT_ID_MESSAGE));
    }
}
