package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;

import org.slf4j.Logger;

public class UserDto {
	public static final String ERROR_OF_WHITE_SPACE = "공백이나 null 없이 입력하세요.";

	public static class Request {
		private String userId;
		private String password;
		private String name;
		private String email;

		public Long getIdNull() {
			return null;
		}
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void isValid(Logger logger) {
			if (isOneMoreBlank()) {
				logger.warn("request : {}", this);
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
		private String id;
		private String userId;
		private String name;
		private String email;

		public Response(User user) {
			this.id = toTextFromLong(user.getId());
			this.userId = user.getUserId();
			this.name = user.getName();
			this.email = user.getEmail();
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}
}
