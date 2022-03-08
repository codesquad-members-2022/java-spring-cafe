package com.kakao.cafe.repository.user;

import com.kakao.cafe.domain.user.User;

public interface UserRepository {

    User save(User user);
}
