package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserCollectionRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    private int userNum = 0;

    @Override
    public User save(User user) {
        user.setUserNum(++userNum);
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public void deleteAll() {
        users = new ArrayList<>();
    }

}
