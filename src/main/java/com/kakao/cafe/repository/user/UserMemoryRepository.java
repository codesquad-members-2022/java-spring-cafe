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

    @Override
    public User save(User user) {
        Optional<User> existingData = findByUserId(user.getUserId());
        if (existingData.isPresent()) {
            existingData.get().updateUser(user.getName(), user.getEmail(), user.getPassword());
        } else {
            store.add(user);
        }

        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream().filter((user -> user.getUserId().equals(userId))).findAny();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
