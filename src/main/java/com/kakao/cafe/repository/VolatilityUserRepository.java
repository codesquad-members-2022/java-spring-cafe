package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VolatilityUserRepository implements UserRepository {

    private final Vector<User> users = new Vector<>();

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        user.setIndex(users.size() + 1);
        return users.add(user) ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findOne(String id) {
        return users.stream().filter(user -> user.ownerOf(id)).findAny();
    }

    public void clear() {
        users.clear();
    }
}
