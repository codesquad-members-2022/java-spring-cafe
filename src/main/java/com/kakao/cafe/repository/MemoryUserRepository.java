package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {

    private final List<User> store = Collections.synchronizedList(new ArrayList<>());

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
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.isSameId(userId))
                .findAny();
    }

    public void clear() {
        store.clear();
    }

}
