package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return store.values().stream()
                .filter(user -> user.getNickname().equals(nickname))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

}
