package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();
    Optional<User> save(User user);
    Optional<User> findOne(String id);
}
