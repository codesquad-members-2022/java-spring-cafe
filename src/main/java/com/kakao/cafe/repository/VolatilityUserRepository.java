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
    public void insertUser(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> selectUser(String id) {
        return users.stream().filter(user -> user.match(id)).findAny();
    }

    public void clear() {
        users.clear();
    }
}
