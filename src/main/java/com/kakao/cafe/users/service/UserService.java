package com.kakao.cafe.users.service;

import com.kakao.cafe.users.controller.dto.UserJoinRequest;
import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import com.kakao.cafe.users.exception.UserNotFountException;
import com.kakao.cafe.users.exception.UserUnsavedException;
import com.kakao.cafe.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User join(UserJoinRequest joinRequest) {
        validateDuplicateUser(joinRequest);
        
        return userRepository.save(User.createWithJoinRequest(joinRequest))
                .orElseThrow(UserUnsavedException::new);
    }

    public User findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFountException::new);
    }

    public List<User> findUsers() {
        return userRepository.findAll()
                .orElse(Collections.emptyList());
    }

    private void validateDuplicateUser(UserJoinRequest joinRequest) throws UserDuplicatedException {
        if (userRepository.findByUserId(joinRequest.getUserId()).isPresent()) {
            throw new UserDuplicatedException();
        }
    }
}
