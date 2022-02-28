package com.kakao.cafe;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    private Integer userNum = 0;

    public void insert(User user) {
        validateUserId(user.getUserId());

        user.setUserNum(++userNum);
        users.add(user);
    }

    public List<User> findAll() {
        return users;
    }

    public void validateUserId(String userId) {
        long count = users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .count();

        if (count > 0) {
            throw new IllegalArgumentException();
        }

    }

    public User findByUserId(String userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

}
