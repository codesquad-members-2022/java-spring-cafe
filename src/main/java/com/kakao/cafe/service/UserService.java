package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserRepository;
import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void userJoin(UserJoinDto dto) {
        userRepository.save(new User(dto.getUserId(), dto.getPassword()
                , dto.getName(), dto.getEmail()));
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId);
    }
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User userUpdate(Long id, UserUpdateDto userUpdateDto) {
        return userRepository.updateUser(id, userUpdateDto);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
