package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return store.keySet().stream()
                .filter(userId -> userId.equals(id))
                .map(store::get)
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean delete(Long id) {
        if (store.containsKey(id)) {
            store.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(Long id, User updateParam) {
        if (findById(id).isPresent()) {
            User findUser = findById(id).get();
            findUser.setUserId(updateParam.getUserId());
            findUser.setPassword(updateParam.getPassword());
            findUser.setName(updateParam.getName());
            findUser.setEmail(updateParam.getEmail());
        }
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}
