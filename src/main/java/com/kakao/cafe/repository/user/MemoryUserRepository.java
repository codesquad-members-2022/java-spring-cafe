package com.kakao.cafe.repository.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.user.User;

@Component
public class MemoryUserRepository implements UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
            .filter(u -> u.isYourId(id))
            .findAny();
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
