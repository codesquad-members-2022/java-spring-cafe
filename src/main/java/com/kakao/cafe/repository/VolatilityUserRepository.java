package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class VolatilityUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> selectAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public Optional<User> insertUser(User user) {
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
