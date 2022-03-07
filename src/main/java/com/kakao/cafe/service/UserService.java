package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. 회원 가입 기능
    // TODO 중복 회원 체크 후 가입 시작하도록 로직을 추가해야 한다.
    //  1. 받아온 user.getUserName 을 통해 userStore 에서 User 를 찾는다.
    //  2. 이미 있다면 예외 발생
    //  3. 없다면 받아온 user 생성
    public void createUser(User user) {
        userRepository.findUser(user.getUserName())
                .ifPresent(foundUser -> {
                    throw new IllegalStateException("중복으로 가입할 수 없습니다.");
                });

        userRepository.createUser(user);
    }

    // 2. 회원 조회 기능
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    // 3. 회원 프로필 조회 기능
    public Optional<User> findUser(String userName) {
        User foundUser = userRepository.findUser(userName)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 유저입니다."));

        // foundUser = null 일 가능성이 있으므로 ofNullable 사용
        return Optional.ofNullable(foundUser);
    }
}
