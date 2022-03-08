package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Long join(User user);

    List<User> findUsers();

    Optional<User> findOneUser(Long id);
}
