package com.kakao.cafe.service;

import com.kakao.cafe.controller.UserDto;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public int signUp(UserDto user) {
        validateDuplicateData(user);
        userRepository.save(new User(user));
        return user.getId();
    }

    //전체 회원 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(int id) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow();
    }

    public int update(UserDto updateUserData) {
        validateDuplicateData(updateUserData);
        User updatedUser = userRepository.save(new User(updateUserData));
        return updatedUser.getId();
    }

    private void validateDuplicateData(UserDto updateData) {
        validateDuplicateUser(updateData.getUserId());
        validateDuplicateEmail(updateData.getEmail());
    }

    private void validateDuplicateUser(String userId) {
        userRepository.findByUserId(userId)
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    private void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new IllegalStateException("이미 사용중인 이메일입니다.");
            });
    }

}
