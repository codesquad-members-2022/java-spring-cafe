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
    private static final String MISMATCHED_PASSWORDS = "[ERROR] 비밀번호가 틀렸습니다.";
    private static final String DUPLICATED_USER_ID = "[ERROR] 존재하는 ID입니다. 다시 입력하세요.";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        duplicateUserIdCheck(user);
        userRepository.save(user);
    }

    private void duplicateUserIdCheck(User inputUser) {
        List<User> users = userRepository.findAll();
        users.forEach(user -> checkIfTheNameIsSame(inputUser, user));
    }

    private void checkIfTheNameIsSame(User inputUser, User user) {
        if (user.checkIfTheIDIsTheSame(inputUser)) {
            throw new IllegalArgumentException(DUPLICATED_USER_ID);
        }
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

    public UserProfileDto findUserProfileByUserId(String userId) {
        User user = findUserByUserId(userId);
        return new UserProfileDto(user.getName(), user.getEmail());
    }

    public User findUserByUserId(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(NON_EXISTENT_MEMBER));
    }

    public void checkPasswordMatch(String userId, String password) {
        User user = findUserByUserId(userId);
        if (!user.isYourPassword(password)) {
            throw new IllegalArgumentException(MISMATCHED_PASSWORDS);
        }
    }

    public void update(String userId, User updateUser) {
        // userRepository.update(userId, updateUser);
    }

    public void clear() {
        userRepository.deleteAll();
    }
}
