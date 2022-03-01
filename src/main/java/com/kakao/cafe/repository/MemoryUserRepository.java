package com.kakao.cafe.repository;

import com.kakao.cafe.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {
    public static final String NO_MEMBER_ID_MESSAGE = "해당 회원 id는 없는 번호 입니다.";
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public Long save(User user) {
        if (user != null) {
            store.put(++sequence, user);
            return sequence;
        }
        return -1L;
    }

    @Override
    public User findById(Long id) {
        if (!isValidRangeId(id)) {
            throw new IllegalArgumentException(NO_MEMBER_ID_MESSAGE);
        }
        if (!isExistedId(id)) {
            throw new IllegalArgumentException(NO_MEMBER_ID_MESSAGE);
        }
        return store.get(id);
    }

    private boolean isValidRangeId(Long id) {
        return id > 0 && id <= sequence;
    }

    private boolean isExistedId(Long id) {
        return store.containsKey(id);
    }


    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean delete(Long id) {
        if (store.containsKey(id)) {
            store.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(Long id, User updateParam) {
        User findUser = findById(id);
        findUser.setUserId(updateParam.getUserId());
        findUser.setPassword(updateParam.getPassword());
        findUser.setName(updateParam.getName());
        findUser.setEmail(updateParam.getEmail());
    }

    public void clearStore() {
        store.clear();
        sequence = 0L;
    }
}
