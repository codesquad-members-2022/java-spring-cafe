package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {
    private static List<User> store = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return store.stream()
                .filter(user -> user.getId().equals(id))
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

    public void clearStore() {
        store.clear();
    }

    public int size() {
        return store.size();
    }
}
