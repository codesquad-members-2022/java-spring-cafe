package com.kakao.cafe.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserMemoryRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserMemoryRepository.class);
    private final List<User> userList = new ArrayList<>();
    private long id = 0L;

    @Override
    public void save(User user) {
        if (!isAlreadyJoin(user)) {
            join(user);
            return;
        }
        User originalUser = findByUserId(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다."));
        originalUser = user;
    }

    private void join(User user) {
        user.setId(++id);
        userList.add(user);
        log.info("userList: {}", userList);
    }

    private boolean isAlreadyJoin(User user) {
        return !Objects.isNull(user.getId());
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userList.stream().filter(s -> s.isTheSameId(userId)).findFirst();
    }
}
