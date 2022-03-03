package com.kakao.cafe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.dto.UserDto;
import com.kakao.cafe.domain.user.dto.UserProfileDto;
import com.kakao.cafe.repository.UserRepository;

@Service
public class UserService {

    private static final String NON_EXISTENT_MEMBER = "[ERROR] 존재하지 않는 멤버입니다.";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<UserDto> findAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        int number = 1;
        for (User user : users) {
            userDtoList.add(new UserDto(number++, user.getUserId(), user.getName(), user.getEmail()));
        }
        return userDtoList;
    }

    public UserProfileDto findUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException(NON_EXISTENT_MEMBER));
        return new UserProfileDto(user.getName(), user.getEmail());
    }
}
