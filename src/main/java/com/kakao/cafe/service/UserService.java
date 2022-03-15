package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public int join(User user) {
        userRepository.save(user);
        return user.getUserIndex();
    }

    //전체 회원목록 조회
    public List<User> findMembers() {
        return userRepository.findAll();
    }

    //회원 index에 해당하는 회원 name을 찾기
    public User findOne(int memberId) {
        return userRepository.findByIndex(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않는 회원입니다."));
    }
}
