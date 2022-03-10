package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, User> storage = new ConcurrentHashMap<>();

    public String save(User user) {
        validateDuplicateUser(user);
        user.setRegDate(LocalDate.now());

        String userName = user.getUserName();
        storage.put(userName, user);
        return userName;
    }

    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(storage.get(userName));
    }

    public Optional<User> findByUserEmail(String userEmail) {
        return storage.values().stream()
                .filter(user -> user.getUserEmail().equals(userEmail))
                .findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    private void validateDuplicateUser(User user) {
        validateDuplicateUserName(user);
        validateDuplicateUserEmail(user);
    }

    private void validateDuplicateUserName(User user) {
        String userName = user.getUserName();
        findByUserName(userName)
                .ifPresent(u -> {
                            throw new IllegalStateException("중복되는 이름이 존재합니다.");
                        }
                );
    }

    private void validateDuplicateUserEmail(User user) {
        String userEmail = user.getUserEmail();
        findByUserEmail(userEmail)
                .ifPresent(u -> {
                    throw new IllegalStateException("중복되는 이메일이 존재합니다.");
                });
    }

}
