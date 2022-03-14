package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        user.setId(store.size() + 1);
        store.add(user);
        return null;
    }

    @Override
    public Optional<User> findById(int id) {
        return store.stream()
            .filter(user -> user.getId() == id)
            .findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }
}
