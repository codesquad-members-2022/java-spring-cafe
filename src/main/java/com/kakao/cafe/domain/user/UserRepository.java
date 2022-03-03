package com.kakao.cafe.domain.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private static Map<Long, User> userMap = new HashMap<>();
    private static long seq = 0L;

    public void save(User user) {
        user.setId(++seq);
        userMap.put(user.getId() , user);
    }

    public User findById(Long id) {
        return userMap.get(id);
    }

    public User findByUserId(String userId) {
        return userMap.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .get();
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public void clear() {
        userMap.clear();
    }
}
