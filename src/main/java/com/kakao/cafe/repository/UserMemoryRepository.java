package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserMemoryRepository implements UserRepository{

    private List<User> users = new ArrayList<>();
    private Long userSize = 0L;

    public Long nextUserSequence() {
        return this.userSize;
    }

    @Override
    public User save(User user) {
        users.add(user);
        addUserSize();

        return user;
    }

    private void addUserSize() {
        this.userSize++;
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
