package com.kakao.cafe.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserMemoryRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserMemoryRepository.class);
    private final List<User> userList = new ArrayList<>();
    private long id = 0L;

    @Override
    public void save(User user) {
        user.setId(id++);
        userList.add(user);
        log.info("userList: {}", userList);
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userList.stream().filter(s -> s.getUserId().equals(userId)).findFirst();
    }
}
