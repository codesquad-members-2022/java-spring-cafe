package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++this.sequence);
        store.put(user.getId(), user);

        return user;
    }
}
