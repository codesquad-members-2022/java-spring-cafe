package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private static ArrayList<User> store = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return store.stream().filter(user -> user.isSame(userId)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return store;
    }
}
