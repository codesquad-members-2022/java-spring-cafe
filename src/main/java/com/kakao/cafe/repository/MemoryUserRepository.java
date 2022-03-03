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
    private final Map<String, User> userMap = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(User user) {
        user.setId(++sequence);
        userMap.put(user.getUserId(), user);
        return user.getId();
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public boolean delete(String userId) {
        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String userId, User updateParam) {
        if (findById(userId).isPresent()) {
            User findUser = findById(userId).get();
            findUser.setUserId(updateParam.getUserId());
            findUser.setPassword(updateParam.getPassword());
            findUser.setName(updateParam.getName());
            findUser.setEmail(updateParam.getEmail());
            return true;
        }
        return false;
    }

    public void clearStore() {
        userMap.clear();
        sequence = 0L;
    }
}
