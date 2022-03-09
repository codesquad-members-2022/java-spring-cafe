package com.kakao.cafe.users.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<Long> save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<List<User>> findAll();

    void deleteAll();
}
