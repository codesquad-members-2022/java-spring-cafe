package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String id);

    List<User> findAll();

    void clearStore();
}
