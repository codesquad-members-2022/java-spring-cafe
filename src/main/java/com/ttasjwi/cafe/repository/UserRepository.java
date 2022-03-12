package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    String save(User user);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserEmail(String userEmail);
    List<User> findAll();
}
