package com.ttasjwi.cafe.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    String save(User user);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserEmail(String userEmail);
    List<User> findAll();
}
