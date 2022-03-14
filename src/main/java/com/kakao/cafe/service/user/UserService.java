package com.kakao.cafe.service.user;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(UserDto userDto);

    Optional<User> findSingleUser(String userId);

    List<User> findAllUsers();

    void isDuplicatedUser(User user);
}
