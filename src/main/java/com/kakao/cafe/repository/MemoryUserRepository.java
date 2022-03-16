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

    public User findByUserId(String userId) {
        User foundUser = null;
        for (User user: store) {
            if(user.isSameUser(userId)){
                foundUser = user;
                break;
            }
        }
        return foundUser;
    }

    public List<User> findAll() {
        return store;
    }
}
