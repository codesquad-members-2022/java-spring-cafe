package com.kakao.cafe.repositoryimpl;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.VolatilityUserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.kakao.cafe.repository.Repository.ENTITY_STATUS.*;

@Repository
public class VolatilityUserRepositoryImpl extends VolatilityUserRepository {

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public synchronized Optional<User> save(User user) {
        Optional<User> other = findOne(user.getUserId());
        ENTITY_STATUS status = other.isEmpty() ? TRANSIENT : DETACHED;
        User result = null;
        switch (status) {
            case TRANSIENT:
                result = persist(user);
                break;
            case DETACHED:
                result = merge(other.get().getIndex(), user);
        }
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
