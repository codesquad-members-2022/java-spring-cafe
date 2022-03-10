package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.user.User;

@Component
public class MemoryUserRepository implements UserRepository {

    private static final String DUPLICATED_USER_ID = "[ERROR] 존재하는 ID입니다. 다시 입력하세요.";
    private static final String NOT_FOUNDED_USER_ID = "[ERROR] 존재하지 않는 ID입니다.";

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void save(User user) {
        duplicateUserIdCheck(user);
        users.add(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
            .filter(u -> u.getUserId().equals(id))
            .findAny();
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    private void duplicateUserIdCheck(User inputUser) {
        for (User user : users) {
            checkIfTheNameIsSame(inputUser, user);
        }
    }

    private void checkIfTheNameIsSame(User inputUser, User user) {
        if (user.getUserId().equals(inputUser.getUserId())) {
            throw new IllegalArgumentException(DUPLICATED_USER_ID);
        }
    }

    public void update(String userId, User updateUser) {
        User user = findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(NOT_FOUNDED_USER_ID));
        user.updateInfo(updateUser);
    }
}
