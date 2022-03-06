package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User user = findOne(userId);
        return new UserResponseDto(user);
    }

    public void clearRepository() {
        userRepository.clear();
    }

    public void updateUserInfo(User user) {
        User target = findOne(user.getUserId());
        checkPassword(user, target);
        userRepository.save(user);
    }

    private void checkDuplicateId(User user) {
        userRepository.findById(user.getUserId()).ifPresent(u -> {
            throw new ClientException(HttpStatus.BAD_REQUEST, "아이디가 이미 존재합니다.");
        });
    }

    private User findOne(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new ClientException(HttpStatus.BAD_REQUEST, "찾으시는 유저가 없습니다.");
                });
    }

    private void checkPassword(User user, User target) {
        if(!target.isSamePassword(user)) {
            throw new ClientException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }
}
