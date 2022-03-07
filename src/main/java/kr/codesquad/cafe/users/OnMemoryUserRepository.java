package kr.codesquad.cafe.users;

import java.util.*;

public class OnMemoryUserRepository implements UserRepository {

    private static Map<Long, User> repository = new HashMap<>();
    private static long nextId = 0L;

    @Override
    public User save(User user) {
        user.setId(++nextId);
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(repository.get(id));
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
