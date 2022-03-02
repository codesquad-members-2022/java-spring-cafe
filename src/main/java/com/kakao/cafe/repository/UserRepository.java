package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String userId);

    Optional<User> findByPassword(String password);

    List<User> findAll();
}
