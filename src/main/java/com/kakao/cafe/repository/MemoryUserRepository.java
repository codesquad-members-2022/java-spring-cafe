package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserRepository implements UserRepository {
    private static final List<User> users = new ArrayList<>();
    private static Long sequence = 0L;

    @Override
    public void save(User user) {
        user.setId(sequence++);
        users.add(user);
    }

    @Override
    public User findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

}
