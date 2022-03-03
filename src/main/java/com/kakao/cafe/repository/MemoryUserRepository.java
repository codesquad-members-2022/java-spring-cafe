package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public class MemoryUserRepository implements UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void save(User user) {
        sequence ++;
        user.setId(sequence);
        store.put(sequence,user);

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
            .filter(m -> m.getEmail().equals(email))
            .findAny();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return store.values().stream()
            .filter(m -> m.getNickname().equals(nickname))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clearStore() {
        store.clear();
    }

}
