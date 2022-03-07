package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.Vector;

public abstract class VolatilityUserRepository implements Repository<User, String> {

    protected final Vector<User> users = new Vector<>();

    protected User persist(User user) {
        user.setIndex(users.size() + 1);
        return users.add(user) ? user : null;
    }

    protected User merge(int index, User user) {
        users.add(index - 1, user);
        return user;
    }
}
