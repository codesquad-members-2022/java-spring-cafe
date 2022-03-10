package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.time.LocalDateTime;
import java.util.*;

public class UserMemoryRepository implements UserRepository {
    private static ArrayList<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        user.setCreatedDate(LocalDateTime.now());
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }

    public void clearStore() { store.clear(); }
}
