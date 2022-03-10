package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.user.User;

@Component
public class UserRepository implements CustomRepository<User> {

    private static final String DUPLICATED_USER_ID = "[ERROR] 존재하는 ID입니다. 다시 입력하세요.";
    private static final String NOT_FOUNDED_USER_ID = "[ERROR] 존재하지 않는 ID입니다.";

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
        duplicateUserIdCheck(user);
        users.add(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
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
        User user = findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException(NOT_FOUNDED_USER_ID));
        user.updateInfo(updateUser);
    }

    public void clear() {
        users.clear();
    }
}
