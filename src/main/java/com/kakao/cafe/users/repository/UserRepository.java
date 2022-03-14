package com.kakao.cafe.users.repository;

import com.kakao.cafe.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<List<User>> findAll();

    void deleteAll();
}
