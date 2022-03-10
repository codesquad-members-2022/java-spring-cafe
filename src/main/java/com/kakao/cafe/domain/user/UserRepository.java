package com.kakao.cafe.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    void clear();
}
