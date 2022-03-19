package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.web.dto.user.LoginUserDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(User user) {
        // TODO : validate duplication and format of email, nickname, password
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparingInt(u -> u.getId().intValue()));
        return users;
    }

    public User findOne(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void updateUser(String userId, User user) {
        userRepository.update(userId, user);
    }

    public User login(String loginUserId, String loginPassword) {
        User loginUser = userRepository.findByUserId(loginUserId);
        if (loginUser == null) {
            return null;
        }

        if (loginUser.getPassword().equals(loginPassword)) {
            return loginUser;
        }

        return null;
    }
}
