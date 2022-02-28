package com.kakao.cafe.domain.users;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public void save(User user) {
        users.add(user);
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(new UserDto(user.getUserId(), user.getName(), user.getEmail()));
        }
        return userDtoList;
    }

}
