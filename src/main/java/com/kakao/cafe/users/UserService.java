package com.kakao.cafe.users;

import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.domain.UserRepository;
import com.kakao.cafe.users.exception.UserDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public Optional<Long> join(User user) {
        validateDuplicateUser(user);

        return userRepository.save(user);
    }

    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    public Optional<List<User>> findUsers() {
        return userRepository.findAll();
    }

    private void validateDuplicateUser(User user) throws UserDuplicatedException {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new UserDuplicatedException("이미 존재하는 회원입니다.");
        }
    }
}
