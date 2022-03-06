package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.exception.DuplicatedIdException;

@Component
public class UserRepository implements CustomRepository<User> {
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream()
            .filter(u -> u.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
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
