package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class MemoryUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();
    private int sequence = 0;

    @Override
    public void save(User user) {
        sequence++;
        user.setId(sequence);
        user.setDate(LocalDate.now().toString());
        users.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream()
            .filter(u -> u.getId() == id)
            .findAny();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return users.stream()
            .filter(u -> u.getNickname().equals(nickname))
            .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
            .filter(u -> u.getEmail().equals(email))
            .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

}
