package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static List<User> userList = new ArrayList<>();

    @Override
    public void save(User user) {
        userList.add(user);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public User findById(String id) {
        for (User user : userList) {
            if (user.getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clearStorage(){
        userList.clear();
    }

}
