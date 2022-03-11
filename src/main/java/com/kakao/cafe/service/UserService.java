package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.JdbcUserRepository;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository) {
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

    public void userUpdate(Long id, UserUpdateDto userUpdateDto) {
        userRepository.updateUser(id, userUpdateDto);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
