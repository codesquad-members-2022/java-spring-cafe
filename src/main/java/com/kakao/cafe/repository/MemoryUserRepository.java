package com.kakao.cafe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public class MemoryUserRepository implements UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByNickName(String nickname) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByPassword(String password) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
