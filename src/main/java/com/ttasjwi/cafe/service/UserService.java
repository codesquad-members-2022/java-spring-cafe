package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.controller.request.UserJoinRequest;
import com.ttasjwi.cafe.controller.response.UserResponse;
import com.ttasjwi.cafe.domain.user.User;
import com.ttasjwi.cafe.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public UserResponse findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(UserResponse::new)
                .orElseThrow(() -> new NoSuchElementException("그런 회원은 존재하지 않습니다."));
    }


    public List<UserResponse> findAll() {
        return userRepository.findAll()
                            .stream()
                            .map(UserResponse::new)
                            .collect(Collectors.toList());
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
