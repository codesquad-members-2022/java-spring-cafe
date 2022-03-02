package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String,User> userHashMap = new HashMap<>();

    @Override
    public void save(User user) {
        userHashMap.put(user.getUserId(),user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userHashMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userHashMap.values();
    }
}
