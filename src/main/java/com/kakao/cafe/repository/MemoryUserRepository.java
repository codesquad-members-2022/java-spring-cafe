package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.isSameUser(userId))
                .findAny();
    }
}
