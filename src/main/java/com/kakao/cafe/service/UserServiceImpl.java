package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Long join(User user) {
        validateDuplicateUserId(user);
        validateUserInfo(user);
        userRepository.save(user);
        return user.getId();
    } // 굳이 Id를 리턴값으로 주어야 할까?

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    private void validateDuplicateUserId(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    private void validateUserInfo(User user) {
        if (user.getUserId().equals("")){
            throw new IllegalStateException("유저 아이디 정보가 없습니다.");
        }

        if (user.getPassword().equals("")){
            throw new IllegalStateException("유저 비밀번호 정보가 없습니다.");
        }

        if (user.getName().equals("")){
            throw new IllegalStateException("유저 이름 정보가 없습니다.");
        }

        if (user.getEmail().equals("")){
            throw new IllegalStateException("유저 이메일 정보가 없습니다.");
        }
    }

    @Override
    public Optional<User> findOneUser(Long id) {
        return userRepository.findById(id);
    }
}
