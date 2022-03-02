package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserMemorySaveRepository implements UserRepository {

    private static final Map<Long, User> userStore = new HashMap<>();
    private long id = 0L;

    @Override
    public User userSave(User user) {
        user.setId(++id);
        userStore.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserId(String userId) {
        return userStore.values()
                .stream()
                .filter(user -> user
                        .isSameUserId(userId))
                .findAny();
    }

    @Override
    public Optional<User> findEmail(String userEmail) {
        return userStore.values()
                .stream()
                .filter(user -> user
                        .isSameUserEmail(userEmail))
                .findAny();
    }

    @Override
    public List<User> findAllUser() {
        return new ArrayList<>(userStore.values());
    }
}
