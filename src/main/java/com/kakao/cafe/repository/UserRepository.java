package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.exception.DuplicatedIdException;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.UserDto;
import com.kakao.cafe.domain.dto.UserProfileDto;

@Repository
public class UserRepository {
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public void save(User user) {
        duplicateUsernameCheck(user);
        users.add(user);
    }

    private void duplicateUsernameCheck(User inputUser) {
        for (User user : users) {
            checkIfTheNameIsSame(inputUser, user);
        }
    }

    private void checkIfTheNameIsSame(User inputUser, User user) {
        if (user.getUserId().equals(inputUser.getUserId())) {
            throw new DuplicatedIdException();
        }
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        int number = 1;
        for (User user : users) {
            userDtoList.add(new UserDto(number++, user.getUserId(), user.getName(), user.getEmail()));
        }
        return userDtoList;
    }

    public UserProfileDto findByUserId(String userId) {
        User user = users.stream()
            .filter(u -> u.getUserId().equals(userId))
            .findAny()
            .orElseThrow();
        return new UserProfileDto(user.getName(), user.getEmail());
    }
}
