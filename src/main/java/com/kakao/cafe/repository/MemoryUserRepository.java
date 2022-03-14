package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static List<User> store = new ArrayList<>();

    @Override
    public User save(User user) {
        if (user.hasId()) {
            return update(user);
        }
        user.setId(store.size() + 1);
        store.add(user);
        return user;
    }

    private User update(User user) {
        store.set(user.getId() - 1, user);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return store.stream()
            .filter(user -> user.getId() == id)
            .findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
            .filter(user -> user.getEmail().equals(email))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }


}
