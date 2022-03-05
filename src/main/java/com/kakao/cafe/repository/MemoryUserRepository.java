package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {
    private static List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return store.stream()
                .filter(user -> user.getId().equals(id))
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
        return store; // 이렇게 바로 주면 안되나?
    }

    public void clearStore() {
        store.clear();
    }
}
