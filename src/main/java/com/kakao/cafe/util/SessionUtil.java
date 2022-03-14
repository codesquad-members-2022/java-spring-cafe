package com.kakao.cafe.util;

import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import java.util.Optional;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static final String SESSION_USER = "SESSION_USER";

    public static UserResponse checkUser(HttpSession session, String userId) {
        UserResponse userResponse = getUser(session);

        if (!userResponse.getUserId().equals(userId)) {
            throw new InvalidRequestException(ErrorCode.INCORRECT_USER);
        }
        return userResponse;
    }

    public static UserResponse getUser(HttpSession session) {
        UserResponse userResponse = (UserResponse) Optional.ofNullable(
                session.getAttribute(SESSION_USER))
            .orElseThrow(() -> new NotFoundException(ErrorCode.SESSION_NOT_FOUND));

        System.out.println("userResponse = " + userResponse);
        return userResponse;
    }

}
