package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

public interface UserRepository {

    Long nextUserSequence();

    User join(User user);

    User findByUserId(String userId);

    void deleteAllUsers();
}
