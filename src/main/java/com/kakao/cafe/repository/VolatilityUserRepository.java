package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class VolatilityUserRepository implements UserRepository {

    private final Vector<User> users = new Vector<>();
    private static final AtomicInteger seq = new AtomicInteger(1);

    @Override
    public List<User> selectAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Optional<User> insertUser(User user) {
        user.setIndex(seq.getAndIncrement());
        return users.add(user) ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> selectUser(String id) {
        return users.stream().filter(user -> user.ownerOf(id)).findAny();
    }

    public void clear() {
        users.clear();
    }
}
