package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.dto.user.LoginUserDto;
import com.kakao.cafe.web.dto.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(UserDto userDto) {
        User signUpUser = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        userRepository.save(signUpUser);
        return signUpUser.getId();
    }

    public List<User> findUsers() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparingInt(u -> u.getId().intValue()));
        return users;
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean isIdSame(String userId, UserDto userDto) {
        User userBefore = findOne(userId);
        if (userBefore.getUserId().equals(userDto.getUserId())) {
            return true;
        }

        return false;
    }

    public boolean isPasswordMatch(String userId, UserDto userDto) {
        User userBefore = findOne(userId);
        if (userBefore.getPassword().equals(userDto.getPassword())) {
            return true;
        }

        return false;
    }

    public void updateUser(String userId, UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
        userRepository.update(userId, user);
    }

    public String login(String loginUserId, String loginPassword) {
        User loginUser = userRepository.findByUserId(loginUserId);
        if (loginUser == null) {
            return null;
        }

        if (loginUser.getPassword().equals(loginPassword)) {
            return loginUser.getUserId();
        }

        return null;
    }
}
