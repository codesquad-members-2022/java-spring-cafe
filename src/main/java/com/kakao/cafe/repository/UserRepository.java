package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void createUser(User user);

    List<User> findAllUsers();

    Optional<User> findUser(String userName);
}
