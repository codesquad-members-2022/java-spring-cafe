package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@org.springframework.stereotype.Repository
public class MemoryUserRepository implements Repository<User, String> {

    private final List<User> store = new CopyOnWriteArrayList<>();

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(List.copyOf(store));
    }

    public void clearStore() {
        store.clear();
    }
}
