package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.*;

public class UserMemoryRepository implements UserRepository {
    private final ArrayList<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.isSameUserId(userId))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
                .filter(user -> user.isSameEmail(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
