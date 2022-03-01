package com.kakao.cafe.user.repository;

import com.kakao.cafe.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMemoryRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserMemoryRepository.class);
    private static final List<User> userList = new ArrayList<>();
    private static long id = 0L;

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
}
