package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public void save(User user) {
        user.setId(sequence.getAndIncrement());
        users.add(user);
    }

    @Override
    public User findById(Long id) {
        return users.stream()
                .filter(user -> user.isEqualsId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 사용자는 존재하지 않습니다."));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public boolean isExistUserId(String userId) {
        return users.stream()
                .anyMatch(user -> user.isEqualsUserId(userId));
    }

    @Override
    public boolean isExistEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.isEqualsEmail(email));
    }

}
