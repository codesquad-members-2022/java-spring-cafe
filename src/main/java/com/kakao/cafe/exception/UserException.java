package com.kakao.cafe.exception;

import com.kakao.cafe.domain.user.User;

import java.util.Map;

public class UserException {
    public static void duplicateException(Map<Long, User> userMap, String userId) {
        if(userMap.values().stream()
                .anyMatch(user -> user.equals(userId))){
            try {
                throw new Exception("아이디 중복 예외입니다");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
