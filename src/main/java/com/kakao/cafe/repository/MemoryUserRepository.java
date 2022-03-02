package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<Long, User> userStore = new HashMap<>();
    private static long userIdx = 0;

    @Override
    public void createUser(User user) {
        user.setUserIdx(++userIdx);
        userStore.put(user.getUserIdx(), user);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public Optional<User> findUser(String userName) {
        return userStore.values().stream()
                .filter(user -> user.getUserName().equals(userName))
                .findAny();
    }

    public Map<Long, User> getUserStore() {
        return userStore;
    }

    public void clear() {
        userStore.clear();
    }
}
