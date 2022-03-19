package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;

import org.slf4j.Logger;

public class UserDto {
	public static final String ERROR_OF_WHITE_SPACE = "공백이나 null 없이 입력하세요.";

	public static class Request {
		private final String userId;
		private final String password;
		private final String name;
		private final String email;

		public Request(String userId, String password, String name, String email) {
			this.userId = userId;
			this.password = password;
			this.name = name;
			this.email = email;
		}

		public String getUserId() {
			return userId;
		}

		public String getPassword() {
			return password;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public void isValid(Logger logger) {
			if (isOneMoreBlank()) {
				throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
			}
		}

		private boolean isOneMoreBlank() {
			return (isNullOrBlank(this.userId) ||
				isNullOrBlank(this.name) ||
				isNullOrBlank(this.password) ||
				isNullOrBlank(this.email));
		}

		@Override
		public String toString() {
			return "User{" +
				"userId='" + userId + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
		}
	}

	public static class Response {
		private final String id;
		private final String userId;
		private final String name;
		private final String email;

		public Response(User user) {
			this.id = toTextFromLong(user.getId());
			this.userId = user.getUserId();
			this.name = user.getName();
			this.email = user.getEmail();
		}

		public String getId() {
			return id;
		}

		public String getUserId() {
			return userId;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}
	}
}
