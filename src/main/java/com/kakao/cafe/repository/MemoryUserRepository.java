package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> userStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        for (int i = 0; i < userStore.size(); i++) {
            if (userStore.get(i) == null) {
                return store(user, i);
            }
        }
        return store(user, userStore.size());
    }

    private User store(User user, int index) {
        user.setIndex(index);
        userStore.add(user);
        return user;
    }

    @Override
    public Optional<User> findByIndex(int index) throws IndexOutOfBoundsException{
        return Optional.ofNullable(userStore.get(index));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userStore.stream()
                .filter(user -> user.compareById(userId))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore);
    }

    public void clearStore() {
        userStore.clear();
    }

    public int size() {
        return userStore.size();
    }

    @Override
    public void update(User user, int index) {
        userStore.set(index, user);
    }
}
