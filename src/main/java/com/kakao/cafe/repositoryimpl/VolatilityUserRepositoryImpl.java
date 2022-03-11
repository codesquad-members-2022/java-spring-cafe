package com.kakao.cafe.repositoryimpl;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.VolatilityUserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VolatilityUserRepositoryImpl extends VolatilityUserRepository {

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());

        User result = other.isEmpty()
                ? persist(user)
                : merge(other.get().getIndex(), user);

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findOne(String id) {
        return users.stream().filter(user -> user.ownerOf(id)).findAny();
    }

    @Override
    public void clear() {
        users.clear();
    }
}
