package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        User userInformation = findByUserId(user.getUserId()).orElse(null);

        if (userInformation == null) {
            users.add(user);
            return user;
        }

        userInformation.update(user);

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream().filter(user -> user.hasSameUserId(userId)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void clear() {
        users.clear();
    }
}
