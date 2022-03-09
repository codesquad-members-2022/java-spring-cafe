package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository{

    private static Map<String,User> store = new HashMap<>();

    public User save(User user) {
        store.put(user.getUserId(),user);
        return user;
    }

    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
