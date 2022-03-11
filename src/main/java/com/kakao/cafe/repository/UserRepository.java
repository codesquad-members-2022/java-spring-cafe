package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long save(User user);

    Optional<User> findByUserId(String userId);

    List<User> findAll();

    boolean delete(String userId);

    boolean update(String userId, User updateParam);
}
