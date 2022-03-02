package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userHashMap = new LinkedHashMap<>();
    private Long userNum = 0L;

    @Override
    public void save(User user) {
        user.setUserNum(++userNum);
        userHashMap.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userHashMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userHashMap.values());
    }
}
