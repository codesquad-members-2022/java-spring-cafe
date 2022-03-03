package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> selectAll();
    void insertUser(User user);
    Optional<User> selectUser(String id);
}
