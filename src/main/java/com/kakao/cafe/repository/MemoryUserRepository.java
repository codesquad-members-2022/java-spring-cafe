package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryUserRepository implements UserRepository{

    private final Map<String, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User findByUserId(String userId) {
        return store.get(userId);
    }

    @Override
    public User findById(Long id) {
        return store.values().stream()
                .filter(user -> user.getId().equals(id))
                .findAny().get();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public void update(String userId, User updateParam) {
        User findUser = store.get(userId);
        findUser.setEmail(updateParam.getEmail());
        findUser.setName(updateParam.getName());
        findUser.setUserId(updateParam.getUserId());
        findUser.setPassword(updateParam.getPassword());
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
