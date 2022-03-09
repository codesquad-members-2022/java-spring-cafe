package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryUserRepository implements UserRepository{

    private List<User> userList = new CopyOnWriteArrayList<>();

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
