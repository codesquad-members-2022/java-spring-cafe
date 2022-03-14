package com.kakao.cafe.common.utils.session;

import static com.kakao.cafe.common.utils.session.SessionUser.*;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

public class SessionUtils {
	public static boolean isValidLogin(HttpSession httpSession, Logger logger) {
		try {
			isLoginAccessor(httpSession);
			return true;
		} catch (IllegalArgumentException exception) {
			logger.error("invalid accessor in private site: {}", exception);
			return false;
		}
	}

	public static boolean isValidAccess(String userId, HttpSession httpSession, Logger logger) {
		try {
			isLoginAccessor(httpSession);
			isTheSameLoginUserAsAccount(userId, httpSession);
			return true;
		} catch (IllegalArgumentException exception) {
			logger.error("invalid access : {}", exception);
			return false;
		}
	}

	private static void isLoginAccessor(HttpSession httpSession) {
		Object accessor = getHttpSessionAttribute(httpSession);
		if (Objects.isNull(accessor)) {
			throw new IllegalArgumentException("로그인 하지 않은 익명의 사용자 입니다.");
		}
	}

	private static void isTheSameLoginUserAsAccount(String userId, HttpSession httpSession) {
		SessionUser sessionUser = (SessionUser)getHttpSessionAttribute(httpSession);
		if (sessionUser.isDifferentFrom(userId)) {
			String message = String.format("로그인 정보와 다른 사용자 입니다. account: %s, login: %s", userId, sessionUser.getUserId());
			throw new IllegalArgumentException(message);
		}
	}

	private static Object getHttpSessionAttribute(HttpSession httpSession) {
		return httpSession.getAttribute(SESSION_KEY);
	}
}
