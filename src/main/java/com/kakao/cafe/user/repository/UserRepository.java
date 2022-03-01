package com.kakao.cafe.user.repository;

import com.kakao.cafe.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    void save(User user);
    List<User> findAll();
}
