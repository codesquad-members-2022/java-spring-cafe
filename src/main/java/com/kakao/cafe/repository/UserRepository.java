package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User userSave(User user);

    Optional<User> findUserId(String userId);

    Optional<User> findEmail(String userEmail);

    List<User> findAllUser();
}
