package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;
import static com.kakao.cafe.user.domain.UserDto.*;

import java.util.Objects;

import org.slf4j.Logger;

public class UserUpdateDto {
	public static class Request {
		private String id;
		private String userId;
		private String name;
		private String email;

		public Long getIdByLong() {
			if (Objects.isNull(this.id)) {
				return null;
			}
			return toLongFromText(this.id);
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

		public void isValidWhenUpdate(Logger logger) {
			if (isNullOrBlank(this.id)) {
				logger.warn("request update user : {}", this);
				throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
			}
			isValid(logger);
		}

		public void isValid(Logger logger) {
			if (isOneMoreBlank()) {
				logger.warn("request : {}", this);
				throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
			}
		}

		private boolean isOneMoreBlank() {
			return isNullOrBlank(this.userId) || isNullOrBlank(this.name) || isNullOrBlank(this.email);
		}

		@Override
		public String toString() {
			return "User{" +
				"userId='" + userId + '\'' +
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
