package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;

import java.util.List;

public interface UserService {

    List<User> searchAll();
    User add(NewUserParam newUserParam);
    User update(ModifiedUserParam modifiedUserParam);
    User search(String id);
}
