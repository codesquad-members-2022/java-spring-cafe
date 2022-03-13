package com.kakao.cafe.domain.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryUserRepository implements UserRepository{

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void clear() {
        users.clear();
    }
}
