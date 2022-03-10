package kr.codesquad.cafe.users;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private static final ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    public User save(User user) {
        user.setId(nextId.getAndIncrement());
        repository.put(user.getId(), user);

        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Optional<User> findByUserId(String userId) {
        return repository.values().stream()
                .filter(user -> user.userIdIs(userId))
                .findAny();
    }

    public Optional<User> findByName(String name) {
        return repository.values().stream()
                .filter(user -> user.nameIs(name))
                .findAny();
    }

    public Optional<User> findByEmail(String email) {
        return repository.values().stream()
                .filter(user -> user.emailIs(email))
                .findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(repository.values());
    }
}
