package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryUserRepository implements CrudRepository<User, String> {

    private final List<User> users = new CopyOnWriteArrayList<>();

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        findById(user.getUserId()).ifPresentOrElse(
                (other) -> merge(other.getId(), user),
                () -> persist(user)
        );
        return Optional.ofNullable(user);
    }

    private void persist(User user) {
        user.setId(users.size() + 1);
        users.add(user);
    }

    private void merge(int index, User user) {
        users.set(index - 1, user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return users.stream().filter(user -> user.ownerOf(userId)).findAny();
    }

    @Override
    public int deleteById(String userId) {
        /* In memory version, I can't think of a good way.. 😂 */
        return 0;
    }
}
