package kr.codesquad.cafe.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> repository = Collections.synchronizedList(new ArrayList<>());

    public void save(User user) {
        findByUserId(user.getUserId())
                .ifPresentOrElse(
                userOld -> update(userOld, user),
                () -> repository.add(user)
        );
    }

    private void update(User userOld, User userNew) {
        repository.set(repository.indexOf(userOld), userNew);
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
