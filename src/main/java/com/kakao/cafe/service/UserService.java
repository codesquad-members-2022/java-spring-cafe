package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.UserSaveDto;
import com.kakao.cafe.controller.dto.UserUpdateDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserSaveDto userSaveDto) {
        validateUserSaveDto(userSaveDto);
        User user = userSaveDto.toEntity();
        userRepository.save(user);
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public void update(UserUpdateDto userUpdateDto) {
        validateUserUpdateDto(userUpdateDto);
        User user = userUpdateDto.toEntity();
        userRepository.save(user);
    }

    private void validateUserSaveDto(UserSaveDto userSaveDto) {
        validateUserId(userSaveDto.getUserId());
        validateEmail(userSaveDto.getEmail());
    }

    private void validateUserUpdateDto(UserUpdateDto userUpdateDto) {
        User findUser = userRepository.findByUserId(userUpdateDto.getUserId());
        if (findUser.isNotEqualsPassword(userUpdateDto.getCurrentPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    private void validateUserId(String userId) {
        boolean isExistUserId = userRepository.isExistUserId(userId);
        if (isExistUserId) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    private void validateEmail(String email) {
        boolean isExistEmail = userRepository.isExistEmail(email);
        if (isExistEmail) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }
}
