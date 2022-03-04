package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.TypeFormatter.*;
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
			if (isIdBlank()) {
				logger.warn("request update user : {}", this);
				throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
			}
			isValid(logger);
		}

		private boolean isIdBlank() {
			return (Objects.isNull(this.id) || this.id.isBlank());
		}

		public void isValid(Logger logger) {
			if (isOneMoreBlank()) {
				logger.warn("request : {}", this);
				throw new IllegalArgumentException(ERROR_OF_WHITE_SPACE);
			}
		}

		private boolean isOneMoreBlank() {
			return isUserIdBlank() || isNameBlank() || isEmailBlank();
		}

		private boolean isEmailBlank() {
			return (Objects.isNull(this.email) || this.email.isBlank());
		}

		private boolean isNameBlank() {
			return (Objects.isNull(this.name) || this.name.isBlank());
		}

		private boolean isUserIdBlank() {
			return (Objects.isNull(this.userId) || this.userId.isBlank());
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
