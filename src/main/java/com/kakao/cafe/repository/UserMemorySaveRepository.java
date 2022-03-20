package com.kakao.cafe.repository;

import com.kakao.cafe.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserMemorySaveRepository implements UserRepository {

    private final List<User> userStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User userSave(User user) {
        userStore.add(user);
        return user;
    }

    @Override
    public Optional<User> findUserId(String userId) {
        return userStore.stream()
                .filter(user -> user
                        .isSameUserId(userId))
                .findAny();
    }

    @Override
    public Optional<User> findEmail(String userEmail) {
        return userStore.stream()
                .filter(user -> user
                        .isSameUserEmail(userEmail))
                .findAny();
    }

    @Override
    public List<User> findAllUser() {
        return Collections.unmodifiableList(userStore);
    }

    /**
     * 앞선 Service 영역에서 findUserId를 통해 null 체크를 하여,
     * 이 부분에서 orElse() 에 null 이 들어올 일은 없으나,
     * get 사용으로 인한 경고를 없애기 위해 orElse(null)을 사용
     *
     * @param user
     * @return
     */
    @Override
    public User userUpdate(User user) {
        int oldUserIndex = userStore
                .indexOf(
                        findUserId(user.getUserId()).orElse(null)
                );
        userStore.set(oldUserIndex, user);
        return user;
    }

    public void clearStore() {
        userStore.clear();
    }
}
