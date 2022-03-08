package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.User;

@Repository
public class MemoryUserRepository implements UserRepository {

    private Map<Integer, User> userIdMap = new HashMap<>();
    private Map<String, User> userNicknameMap = new HashMap<>(); // 닉네임으로 검색하기 위해 추가
    private Map<String, User> userEmailMap = new HashMap<>();
    private int sequence = 0;

    @Override
    public void save(User user) {
        sequence++;
        user.setId(sequence);
        user.setDate(LocalDate.now().toString());
        userIdMap.put(sequence, user);
        userNicknameMap.put(user.getNickname(), user);
        userEmailMap.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findById(int id) {
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

}
