package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.exception.DuplicatedIdException;

@Repository
public class UserRepository {
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream()
            .filter(u -> u.getUserId().equals(userId))
            .findAny();
    }
    public List<User> findAll() {
        return List.copyOf(users);
    }

    public void save(User user) {
        duplicateUsernameCheck(user);
        users.add(user);
    }

    private void duplicateUsernameCheck(User inputUser) {
        for (User user : users) {
            checkIfTheNameIsSame(inputUser, user);
        }
    }

    private void checkIfTheNameIsSame(User inputUser, User user) {
        if (user.getUserId().equals(inputUser.getUserId())) {
            throw new DuplicatedIdException();
        }
    }
}
