package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kakao.cafe.domain.User;

public class MemoryUserRepository implements UserRepository {

    private Map<Long, User> userIdMap = new HashMap<>();
    private Map<String, User> userNicknameMap = new HashMap<>(); // 닉네임으로 검색하기 위해 추가
    private Map<String, User> userEmailMap = new HashMap<>(); // 닉네임으로 검색하기 위해 추가
    private long sequence = 0L;

    @Override
    public void save(User user) {
        LocalDate today = LocalDate.now();
        sequence++;
        user.setId(sequence);
        user.setDate(today.toString());
        userIdMap.put(sequence, user);
        userNicknameMap.put(user.getNickname(), user);
        userEmailMap.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userIdMap.get(id));
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return Optional.ofNullable(userNicknameMap.get(nickname));
    }

    @Override
    public Optional<Object> findByEmail(String email) {
        return Optional.ofNullable(userEmailMap.get(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userIdMap.values());
    }

    @Override
    public void clearStore() {
        userIdMap.clear();
    }

}
