package kr.codesquad.cafe.users;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> repository = Collections.synchronizedList(new ArrayList<>());

    public void save(User user) {
        repository.add(user);
    }

    public Optional<User> findByUserId(String userId) {
        return repository.stream()
                .filter(user -> user.userIdIs(userId))
                .findAny();
    }

    public Optional<User> findByName(String name) {
        return repository.stream()
                .filter(user -> user.nameIs(name))
                .findAny();
    }

    public Optional<User> findByEmail(String email) {
        return repository.stream()
                .filter(user -> user.emailIs(email))
                .findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(repository);
    }
}
