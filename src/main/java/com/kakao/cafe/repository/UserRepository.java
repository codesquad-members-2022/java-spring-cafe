package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByEmail(String Email);

    List<User> findAll();
}
