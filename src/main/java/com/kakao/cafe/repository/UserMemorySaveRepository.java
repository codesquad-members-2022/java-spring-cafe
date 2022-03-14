package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserMemorySaveRepository implements UserRepository {

    private static final List<User> userStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User userSave(User user) {
        userStore.add(user);
        return user;
    }

    @Override
    public Optional<User> findUserId(String userId) {
        return userStore.stream()
                .filter(user -> user
                        .isSameUserId(userId))
                .findAny();
    }

    @Override
    public Optional<User> findEmail(String userEmail) {
        return userStore.stream()
                .filter(user -> user
                        .isSameUserEmail(userEmail))
                .findAny();
    }

    @Override
    public List<User> findAllUser() {
        return userStore;
    }

    public void clearStore() {
        userStore.clear();
    }
}
