package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static List<User> store = new ArrayList<>();
    private static Long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
            .filter(user -> user.getEmail().equals(email))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(store);
    }
}
