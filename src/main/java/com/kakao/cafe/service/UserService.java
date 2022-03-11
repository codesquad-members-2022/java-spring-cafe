package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long join(User user){
        validateDuplicateUser(user);
        userRepository.join(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    public List<User> findMembers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }


}
