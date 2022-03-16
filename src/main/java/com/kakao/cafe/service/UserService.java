package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.repository.DomainRepository;
import com.kakao.cafe.util.DomainMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.UserDomainMessage.*;

@Service
public class UserService {

    private final DomainRepository<User, String> userRepository;
    private final DomainMapper<User> userMapper = new DomainMapper<>();

    public UserService(DomainRepository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User add(NewUserParam newUserParam) {
        User newUser = userMapper.convertToDomain(newUserParam, User.class);
        validateDuplicateUser(newUser);

        return userRepository.save(newUser)
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
        modifiedUserParam.switchPassword();

        User modifiedUser = userMapper.convertToDomain(modifiedUserParam, User.class);
        return userRepository.save(modifiedUser)
                .orElseThrow(() -> new SaveUserException(HttpStatus.BAD_GATEWAY, UPDATE_FAIL_MESSAGE));
    }

    public User search(String userId) {
        return userRepository.findOne(userId)
                .orElseThrow(() -> new NoSuchUserException(HttpStatus.OK, NO_SUCH_USER_MESSAGE));
    }
}
