package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.kakao.cafe.domain.User;

public class MemoryUserRepository implements UserRepository {
    private final List<User> users = new CopyOnWriteArrayList<>();
    private final AtomicInteger sequence = new AtomicInteger();

    @Override
    public int save(User user) {
        sequence.compareAndSet(users.size(), users.size() + 1);
        int id = sequence.get();
        user.setId(id);
        user.setDate(LocalDate.now());
        users.add(user);
        return id;
    }

    @Override
    public void update(User user) {
    }
    // TODO: H2UserRepository 에서는 쓰이지만 여기서는 쓸 일이 없어서 어떻게 처리해야할지 모르겠다.

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
