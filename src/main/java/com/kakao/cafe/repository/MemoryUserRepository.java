package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        for (int i = 0; i <= store.size(); i++) {
            if (store.get(i) == null) {
                store.add(i, user);
                return user;
            }
        }
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByIndex(int index) {
        if (store.size() > index) {
            return Optional.ofNullable(store.get(index));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
                .filter(user -> user.compareById(userId))
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
