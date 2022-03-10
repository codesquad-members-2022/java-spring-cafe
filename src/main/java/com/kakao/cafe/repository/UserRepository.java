package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.user.User;

public interface UserRepository {
    List<User> findAll();

    void save(User user);

    Optional<User> findById(String id);

    void deleteAll();
}
