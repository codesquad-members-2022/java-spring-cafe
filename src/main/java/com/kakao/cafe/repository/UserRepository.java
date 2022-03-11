package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByIndex(int index);

    Optional<User> findByUserId(String userId);

    List<User> findAll();

    void clearStore();
}
