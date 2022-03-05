package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Long nextUserSequence();

    User save(User user);

    Optional<User> findByUserId(String userId);

    void deleteAllUsers();

    List<User> findAll();
}
