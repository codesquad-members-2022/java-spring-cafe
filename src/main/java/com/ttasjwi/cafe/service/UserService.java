package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.controller.UserJoinRequest;
import com.ttasjwi.cafe.domain.user.User;
import com.ttasjwi.cafe.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(UserJoinRequest userJoinRequest) {
        validateDuplicateUser(userJoinRequest);

        User user = userJoinRequest.toEntity();
        userRepository.save(user);
        return user.getUserName();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException("그런 회원은 존재하지 않습니다."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void validateDuplicateUser(UserJoinRequest userJoinRequest) {
        validateDuplicateUserName(userJoinRequest);
        validateDuplicateUserEmail(userJoinRequest);
    }

    private void validateDuplicateUserName(UserJoinRequest userJoinRequest) {
        String userName = userJoinRequest.getUserName();
        userRepository.findByUserName(userName)
                .ifPresent(u -> {
                            throw new IllegalStateException("중복되는 이름이 존재합니다.");
                        }
                );
    }

    private void validateDuplicateUserEmail(UserJoinRequest userJoinRequest) {
        String userEmail = userJoinRequest.getUserEmail();
        userRepository.findByUserEmail(userEmail)
                .ifPresent(u -> {
                    throw new IllegalStateException("중복되는 이메일이 존재합니다.");
                });
    }
}
