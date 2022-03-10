package com.kakao.cafe.repository.user;


import com.kakao.cafe.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserMemoryRepository implements UserRepository {

    private static List<User> store = new ArrayList<>();
    private static Long id = 1L;

    @Override
    public User save(User user) {
        user.setId(id++);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream().filter((user -> user.equals(userId))).findAny();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void clear() {
        store.clear();
        id = 1L;
    }
}
