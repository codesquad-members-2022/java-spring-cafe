package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@org.springframework.stereotype.Repository
public class VolatilityUserRepository implements Repository<User, String> {

    private final Vector<User> users = new Vector<>();

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());

        User result = other.isEmpty()
                ? persist(user)
                : merge(other.get().getId(), user);

        return Optional.ofNullable(result);
    }
    private User persist(User user) {
        user.setId(users.size() + 1);
        return users.add(user) ? user : null;
    }
    private User merge(long index, User user) {
        users.set((int)index - 1, user);
        return user;
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
