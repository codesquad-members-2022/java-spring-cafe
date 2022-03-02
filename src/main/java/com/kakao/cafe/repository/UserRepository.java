package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static List<User> userList = new ArrayList<>();

    public void save(User user) {
        userList.add(user);
    }

}
