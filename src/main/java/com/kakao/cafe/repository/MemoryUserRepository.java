package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {
    private static List<User> store = new ArrayList<>();
    private static int sequence = 0;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return store.stream()
                .filter(user -> user.getId() == id)
                .findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }

    public int size() {
        return store.size();
    }
}
