package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository{

    private final Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(Long id, User updateParam) {
        User findUser = store.get(id);
        findUser.setEmail(updateParam.getEmail());
        findUser.setUserId(updateParam.getUserId());
        findUser.setPassword(updateParam.getPassword());
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
