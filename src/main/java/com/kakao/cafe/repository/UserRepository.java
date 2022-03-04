package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserInformation;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    UserInformation save(UserInformation userInformation);
    Optional<UserInformation> findByUserId(String userId);
    List<UserInformation> findAll();
    void clear();
}
