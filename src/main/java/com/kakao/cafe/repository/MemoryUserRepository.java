package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        checkDuplicateUser(user);
        user.setId(sequence++);
        store.put(user.getId(), user);
        return user;
    }

    private void checkDuplicateUser(User user) {
        findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }


    public void clear() {
        store.clear();
    }

}
