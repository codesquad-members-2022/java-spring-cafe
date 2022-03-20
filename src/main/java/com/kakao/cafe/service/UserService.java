package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;
import com.kakao.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public UserDto findSingleUser(String userId) {
        User user = repository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("일치하는 유저가 존재하지 않습니다."));
        return user.convertToUserDto();
    }

    public List<UserDto> findAllUsers() {
        return repository.findAll().stream()
                .map(User::convertToUserDto)
                .collect(Collectors.toList());
    }

    public void isDuplicatedUser(User user) {
        if (repository.findByUserId(user.getUserId()).isPresent()) {
            throw new IllegalStateException("사용자 ID가 중복됩니다.");
        }
    }


    public void updateUser(UserDto userDto) {
        repository.save(userDto.convertToUser());
    }
}
