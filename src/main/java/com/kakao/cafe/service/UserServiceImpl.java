package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
    회원가입
     */
    @Override
    public String join(User user) {
        userRepository.save(user);
        return user.getId();
    } // 굳이 Id를 리턴값으로 주어야 할까?

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

}
