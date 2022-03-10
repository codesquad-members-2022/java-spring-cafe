package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static final List<User> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public int save(User user) {
        store.add(user);
        return store.size() - 1;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
            .filter(user -> user.hasSameEmail(email))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(store);
    }

    public void clear() {
        store.clear();
    }
}
