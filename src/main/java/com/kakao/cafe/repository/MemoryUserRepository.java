package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository{

    private final List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return store.stream()
                .filter(user -> user.isCorrectId(id))
                .findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.stream()
                .filter(user -> user.isCorrectName(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return store;
    }

    @Override
    public User update(User savedUser, User newUser) {
        delete(savedUser);
        return save(newUser);
    }

    @Override
    public void delete(User user) {
        store.remove(user);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
