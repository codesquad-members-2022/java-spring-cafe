package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMemoryRepository implements UserRepository{

    private List<User> users;
    private Long userSize = 0L;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    public Long nextUserSequence() {
        return this.userSize;
    }

    @Override
    public User join(User user) {
        users.add(user);
        addUserSize();

        return user;
    }

    public void addUserSize() {
        this.userSize++;
    }

    @Override
    public User findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElse(null);
    }

    @Override
    public void deleteAllUsers() {
        this.users = new ArrayList<>();
    }
}
