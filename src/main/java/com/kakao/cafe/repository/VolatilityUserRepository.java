package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VolatilityUserRepository implements DomainRepository<User, String> {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());
        if (other.isPresent()) {
            return merge(other.get().getId(), user);
        }
        return persist(user);
    }

    private Optional<User> persist(User user) {
        user.setId(users.size() + 1);
        users.add(user);
        return Optional.ofNullable(user);
    }

    private Optional<User> merge(int index, User user) {
        users.set(index - 1, user);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findOne(String userId) {
        return users.stream().filter(user -> user.ownerOf(userId)).findAny();
    }
}
