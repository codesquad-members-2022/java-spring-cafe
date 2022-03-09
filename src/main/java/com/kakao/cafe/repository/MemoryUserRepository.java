package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> users = new HashMap<>();
    private static long sequence = 0L;


    @Override
    public User join(User user) {
        user.setId(++sequence);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void clearStore(){
        users.clear();
    }
}
