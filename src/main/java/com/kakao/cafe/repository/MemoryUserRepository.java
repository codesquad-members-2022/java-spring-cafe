package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        User userInformation = findByUserId(user.getUserId());

        if (userInformation == null) {
            users.add(user);
            return user;
        }

        userInformation.updateUserInformation(user);

        return user;
    }

    @Override
    public User findByUserId(String userId) {
        for (User user : users) {
            if (user.hasSameUserId(userId)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void clear() {
        users.clear();
    }
}
