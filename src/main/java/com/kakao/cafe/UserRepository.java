package com.kakao.cafe;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    private Integer userNum = 0;

    public void insert(User user) {
        user.setUserNum(++userNum);
        users.add(user);
    }

    public List<User> findAll() {
        return users;
    }

}
