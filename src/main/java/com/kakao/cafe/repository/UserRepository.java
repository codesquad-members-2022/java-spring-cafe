package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    Optional<Object> findByEmail(String email);

    List<User> findAll();
}
