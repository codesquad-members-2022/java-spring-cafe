package com.kakao.cafe;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    private int userNum = 0;

    public User save(User user) {
        validateUserId(user.getUserId());

        user.setUserNum(++userNum);
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public void validateUserId(String userId) {
        long count = users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .count();

        if (count > 0) {
            throw new IllegalArgumentException("이미 등록된 유저 아이디입니다.");
        }

    }

    public User findByUserId(String userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 아이디입니다."));
    }

    public Integer getUserNum() {
        return userNum;
    }
}
