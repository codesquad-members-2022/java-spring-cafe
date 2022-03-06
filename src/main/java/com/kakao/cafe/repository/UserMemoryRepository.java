package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class UserMemoryRepository implements UserRepository {
    private static Map<String, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        store.put(user.getEmail(), user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() { store.clear(); }
}
