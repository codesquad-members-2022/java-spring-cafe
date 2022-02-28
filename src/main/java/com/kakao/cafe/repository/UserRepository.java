package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);
    User findById(String id);
    List<User> findAll();

}
