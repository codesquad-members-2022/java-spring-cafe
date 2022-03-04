package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.repository.UniqueFieldDuplicatedException;
import com.kakao.cafe.exception.repository.RequiredFieldNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class MemoryUserRepository implements UserRepository {

    private final List<User> userRegistry = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<Long> save(User user) {
        validateRequiredField(user);
        validateUserIdUnique(user);

        User idGeneratedUser = getIdGeneratedUser(user);
        userRegistry.add(idGeneratedUser);
        return Optional.of(idGeneratedUser.getId());
    }

    private User getIdGeneratedUser(User user) {
        return new User.Builder()
                .setId(idGenerator.getAndIncrement())
                .setUserId(user.getUserId())
                .setPasswd(user.getPasswd())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setCreatedDate(user.getCreatedDate())
                .setModifiedDate(user.getModifiedDate())
                .build();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRegistry.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRegistry.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.of(Collections.unmodifiableList(userRegistry));
    }

    @Override
    public void deleteAll() {
        userRegistry.clear();
    }

    private void validateRequiredField(User user) {
        if (user.getUserId() == null || user.getPasswd() == null ||
                user.getName() == null || user.getEmail() == null ) {
            throw new RequiredFieldNotFoundException("필수 정보가 없습니다.");
        }
    }

    private void validateUserIdUnique(User user) {
        if (findByUserId(user.getUserId()).isPresent()) {
            throw new UniqueFieldDuplicatedException("userId 는 중복될 수 없습니다.");
        }
    }
}
