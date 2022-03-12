package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> storage = new ConcurrentHashMap<>();

    @Override
    public String save(User user) {
        String userName = user.getUserName();
        storage.put(userName, user);
        return userName;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(storage.get(userName));
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        return storage.values().stream()
                .filter(user -> user.getUserEmail().equals(userEmail))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

}
