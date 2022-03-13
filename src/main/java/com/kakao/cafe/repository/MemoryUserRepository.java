package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> users = new CopyOnWriteArrayList<>();
    private final AtomicInteger sequence = new AtomicInteger();

    @Override
    public void save(User user) {
        sequence.compareAndSet(users.size(), users.size() + 1);
        int id = sequence.get();
        user.setId(id);
        user.setDate(LocalDate.now());
        users.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream().filter(u -> u.matchesId(id)).findAny();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return users.stream().filter(u -> u.matchesNickname(nickname)).findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream().filter(u -> u.matchesEmail(email)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
