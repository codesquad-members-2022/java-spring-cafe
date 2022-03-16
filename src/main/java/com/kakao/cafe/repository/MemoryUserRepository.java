package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userHashMap = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public void save(User user) {
        userHashMap.put(user.getId(), user);
    }

    @Override
    public void update(User previousUser, User newUser) {
        previousUser.update(newUser);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userHashMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userHashMap.values());
    }
}
