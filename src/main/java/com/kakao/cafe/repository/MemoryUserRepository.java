package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        try {
            int index = findExistingUserId(user.getUserId());
            if (index == -1) {
                users.add(user);
                return user;
            }

            users.set(index, user);
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    private int findExistingUserId(String userId) {
        return IntStream.range(0, users.size())
                .filter(i -> users.get(i).hasSameUserId(userId))
                .findFirst().orElse(-1); // 해당되는 userId가 없는 경우 -1 반환
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
               .filter(user -> user.hasSameUserId(userId))
               .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void clear() {
        users.clear();
    }
}
