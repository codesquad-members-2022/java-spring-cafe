package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {

    private static List<User> userList = new ArrayList<>();
    private Long sequence = 0L;

    @Override
    public User save(User user) {
        userList.add(user);
        return user;
    }
}
