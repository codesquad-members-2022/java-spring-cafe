package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(UserDto userDto) {
        User user = userDto.convertToUser();

        isDuplicatedUser(user);
        repository.save(user);
    }

    public User findSingleUser(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(()-> new NoSuchElementException("일치하는 유저가 존재하지 않습니다."));
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public void isDuplicatedUser(User user) {
        if (repository.findByUserId(user.getUserId()).isPresent()) {
            throw new IllegalStateException("사용자 ID가 중복됩니다.");
        }
    }

    public void updateUser(String userId, UserDto userDto) {
        repository.save(userDto.convertToUser());
    }
}
