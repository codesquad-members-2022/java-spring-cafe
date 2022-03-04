package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User join(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();

}
