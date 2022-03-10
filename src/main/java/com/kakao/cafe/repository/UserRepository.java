package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public interface UserRepository {
    void save(User user);

    void update(User user, User updatedUser);

    Optional<User> findById(int id);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
