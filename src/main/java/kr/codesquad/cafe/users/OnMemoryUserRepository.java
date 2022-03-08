package kr.codesquad.cafe.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class OnMemoryUserRepository implements UserRepository {

    private static final ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    @Override
    public User save(User user) {
        user.setId(nextId.getAndIncrement());
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return repository.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        return repository.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.values());
    }
}
