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
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(User user) {
        user.setId(++sequence);
        userMap.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userMap.keySet().stream()
                .filter(userId -> userId.equals(id))
                .map(userMap::get)
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public boolean delete(Long id) {
        if (userMap.containsKey(id)) {
            userMap.remove(id);
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
        userMap.clear();
        sequence = 0L;
    }
}
