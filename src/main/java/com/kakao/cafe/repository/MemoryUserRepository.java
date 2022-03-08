package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository {

    private List<User> userList = new ArrayList<>();

    @Override
    public User save(User user) {
        userList.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        User selectedUser = null;
        for (User user : userList) {
            if (Objects.equals(user.getUserId(), id)) {
                selectedUser = user;
                break;
            }
        }
        return Optional.ofNullable(selectedUser);
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(this.userList);
    }

    public void clearUserList() {
        this.userList.clear();
    }
}
