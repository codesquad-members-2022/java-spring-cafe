package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository{

    private static List<User> store = new ArrayList<>();

    public User save(User user) {
        store.add(user);
        return user;
    }

    public Optional<User> findByUserId(String userId) {
        User foundUser = null;
        for (User user: store) {
            if(userId.equals(user.getUserId())){
                foundUser = user;
                break;
            }
        }
        return Optional.ofNullable(foundUser);
    }

    public List<User> findAll() {
        return store;
    }
}
