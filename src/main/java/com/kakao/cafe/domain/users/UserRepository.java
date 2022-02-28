package com.kakao.cafe.domain.users;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepository {
    private final List<User> users;

    public void save(User user) {
        users.add(user);
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build());
        }
        return userDtoList;
    }

}
