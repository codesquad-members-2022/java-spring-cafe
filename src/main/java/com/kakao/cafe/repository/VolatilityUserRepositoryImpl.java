package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VolatilityUserRepositoryImpl extends VolatilityUserRepository {

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());
        user = other.isEmpty() ? persist(user) : merge(other.get().getIndex(), user);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findOne(String id) {
        return users.stream().filter(user -> user.ownerOf(id)).findAny();
    }

    @Override
    public void clear() {
        users.clear();
    }
}
