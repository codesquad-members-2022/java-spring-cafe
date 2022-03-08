package com.kakao.cafe.domain.user;

import com.kakao.cafe.exception.UserException;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private static Map<Long, User> userMap = new HashMap<>();
    private static long seq = 0L;

    public void save(User user) {
        user.setId(++seq);
        userMap.put(user.getId() , user);
    }

    public User findById(Long id) {
        return userMap.get(id);
    }

    public User findByUserId(String userId) {
        UserException.duplicateException(userMap, userId);
        return userMap.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userMap.get(id);
        user.updateProfile(userUpdateDto);
        return userMap.put(id, user);
    }

    public void clear() {
        userMap.clear();
    }
}
