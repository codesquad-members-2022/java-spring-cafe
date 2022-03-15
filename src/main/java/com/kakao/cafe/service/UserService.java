package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.repository.DomainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.UserDomainMessage.*;

@Service
public class UserService {

    private final DomainRepository<User, String> userRepository;

    public UserService(DomainRepository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User add(NewUserParam newUserParam) {
        User user = newUserParam.convertToUser();
        validateDuplicateUser(user);

        return userRepository.save(user)
                .orElseThrow(() -> new SaveUserException(HttpStatus.BAD_GATEWAY, ADD_FAIL_MESSAGE));
    }
    private void validateDuplicateUser(User user) {
        userRepository.findOne(user.getUserId())
                .ifPresent(id -> {
                    throw new DuplicateUserException(HttpStatus.OK, DUPLICATE_USER_MESSAGE);
                });
    }

    public User update(ModifiedUserParam modifiedUserParam) {
        modifiedUserParam.isValidRequest();
        return userRepository.save(modifiedUserParam.convertToUser())
                .orElseThrow(() -> new SaveUserException(HttpStatus.BAD_GATEWAY, UPDATE_FAIL_MESSAGE));
    }

    public User search(String userId) {
        return userRepository.findOne(userId)
                .orElseThrow(() -> new NoSuchUserException(HttpStatus.OK, NO_SUCH_USER_MESSAGE));
    }
}
