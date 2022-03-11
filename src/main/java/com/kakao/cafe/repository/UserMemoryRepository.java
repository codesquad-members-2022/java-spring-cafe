package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserMemoryRepository implements UserRepository{

    private List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public User save(User user) {
        if (isExistUser(user)) {
            return user;
        }
        users.add(user);

        return user;
    }

    private boolean isExistUser(User user) {
        return users.stream()
                .anyMatch(eachUser -> eachUser.isCorrectUser(user.getUserId()));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public void deleteAllUsers() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
