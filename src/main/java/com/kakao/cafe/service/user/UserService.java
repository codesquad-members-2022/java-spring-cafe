package com.kakao.cafe.service.user;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserForm;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(UserForm userForm);

    Optional<User> findSingleUser(Long id);

    Optional<User> findSingleUser(String userId);

    List<User> findAllUsers();
}
