package com.kakao.cafe.users.repository;

import com.kakao.cafe.users.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final List<User> userRegistry = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<User> save(User user) {
        user.setId(idGenerator.getAndIncrement());
        userRegistry.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRegistry.stream()
                .filter(user -> user.equalsId(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRegistry.stream()
                .filter(user -> user.equalsUserId(userId))
                .findFirst();
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.of(Collections.unmodifiableList(userRegistry));
    }

    @Override
    public void deleteAll() {
        userRegistry.clear();
    }
}
