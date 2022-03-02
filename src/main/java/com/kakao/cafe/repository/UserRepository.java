package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    boolean delete(Long id);

    void update(Long id, User updateParam);
}
