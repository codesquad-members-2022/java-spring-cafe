package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.domain.user.User;
import com.ttasjwi.cafe.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        validateDuplicateUser(user);
        user.setRegDate(LocalDate.now());
        userRepository.save(user);
        return user.getUserName();
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NoSuchElementException("그런 회원은 존재하지 않습니다."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void validateDuplicateUser(User user) {
        validateDuplicateUserName(user);
        validateDuplicateUserEmail(user);
    }

    private void validateDuplicateUserName(User user) {
        String userName = user.getUserName();
        userRepository.findByUserName(userName)
                .ifPresent(u -> {
                            throw new IllegalStateException("중복되는 이름이 존재합니다.");
                        }
                );
    }

    private void validateDuplicateUserEmail(User user) {
        String userEmail = user.getUserEmail();
        userRepository.findByUserEmail(userEmail)
                .ifPresent(u -> {
                    throw new IllegalStateException("중복되는 이메일이 존재합니다.");
                });
    }
}
