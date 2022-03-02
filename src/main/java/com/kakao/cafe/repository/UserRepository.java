package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User userSave(User user);

    Optional<User> findUserId(String userId);

    Optional<User> findEmail(String userEmail);

    List<User> findAllUser();
}
