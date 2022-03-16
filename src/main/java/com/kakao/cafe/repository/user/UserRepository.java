package com.kakao.cafe.repository.user;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.user.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

    void deleteAll();
}
