package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> store = new ArrayList<>();
    private final int CORRECT_INDEX = 1;

    @Override
    public User save(User user) {
        store.add(user);
        user.setUserIndex(store.indexOf(user) + CORRECT_INDEX);
        user.setJoinTime(LocalDateTime.now());
        return user;
    }

    @Override
    public Optional<User> findByIndex(int userIndex) {
        return Optional.ofNullable(store.get(userIndex - CORRECT_INDEX));
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    public void clearStore() {
        store.clear();
    }
}
