package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        checkDuplicateId(user);
        userRepository.save(user);
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto findUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            return new UserResponseDto(optionalUser.get());
        }
        throw new IllegalArgumentException();
    }

    public void clearRepository() {
        userRepository.clear();
    }

    private void checkDuplicateId(User user) {
        userRepository.findById(user.getUserId()).ifPresent(u -> {
            throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        });
    }
}
