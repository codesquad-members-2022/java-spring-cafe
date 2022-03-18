package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    int save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    List<User> findAll();
}
