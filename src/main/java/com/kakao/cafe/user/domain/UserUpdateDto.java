package com.kakao.cafe.user.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;
import static com.kakao.cafe.user.domain.UserDto.*;

import java.util.Objects;

import org.slf4j.Logger;

public class UserUpdateDto {
	public static final String USER_MESSAGE_OF_EXCEED_PASSWORD_ENTRY = "비밀번호 오류제한 3회 초과 했습니다. (10분 제한시간)";

	/**
	 * 개인정보 수정시 비밀전호 시도 횟수를 기록한 UserUpdateDto.Request 를 통해
	 *  제한 횟수를 넘을 시 전달 메시지 작성하여 전달합니다.
	 */
	public static class WrongPasswordResponse {
		private static int LIMIT_NUMBER_OF_CHANGING_PASSWORD = 2;
		private String message;
		private int numberOfChangingPassword;
		private boolean invalidPasswordEntry;

		public static WrongPasswordResponse from(UserUpdateDto.Request userDto) {
			WrongPasswordResponse response = new WrongPasswordResponse();
			response.numberOfChangingPassword = userDto.getNumberOfChangingPassword();
			response.message = getMessageAboutAttemptPassword(response);
			response.invalidPasswordEntry = false;
			return response;
		}

		public void restrictPasswordEntry() {
			this.invalidPasswordEntry = true;
		}

		private static String getMessageAboutAttemptPassword(WrongPasswordResponse response) {
			if (response.invalidPasswordEntry || response.isValidChangingPassword()) {
				return String.format("비밀번호를 다시 입력하세요. %d회 남았습니다.",
					response.getNumberOfPossibleChangingPassword());
			} else {
				return "비밀번호 오류제한 3회 초과 했습니다. 10분 후 다시 시도해 주세요.";
			}
		}

		public int getNumberOfPossibleChangingPassword() {
			return LIMIT_NUMBER_OF_CHANGING_PASSWORD - numberOfChangingPassword;
		}

		public boolean isValidChangingPassword() {
			return this.numberOfChangingPassword < LIMIT_NUMBER_OF_CHANGING_PASSWORD;
		}

		public void addCount() {
			this.numberOfChangingPassword++;
		}
	}

	public static class Request {
		private String id;
		private String userId;
		private String name;
		private String email;
		private String password;
		private int numberOfChangingPassword;

		public Long getIdByLong() {
			if (Objects.isNull(this.id)) {
				return null;
			}
			return toLongFromText(this.id);
		}

		public String getId() {
			return id;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public void setId(String id) {
			this.id = id;
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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getNumberOfChangingPassword() {
			return numberOfChangingPassword;
		}

		public void setNumberOfChangingPassword(int count) {
			this.numberOfChangingPassword = count;
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
			return isNullOrBlank(this.name) || isNullOrBlank(this.email) || isNullOrBlank(this.password);
		}

		@Override
		public String toString() {
			return "Request{" +
				"id='" + id + '\'' +
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
		private String message;
		private boolean isMessage;

		public Response(User user) {
			this.id = toTextFromLong(user.getId());
			this.userId = user.getUserId();
			this.name = user.getName();
			this.email = user.getEmail();
			this.message = null;
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

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			if (isNullOrBlank(message)) {
				this.isMessage = false;
			}
			this.message = message;
			this.isMessage = true;
		}

		public boolean hasMessage() {
			return isMessage;
		}

		@Override
		public String toString() {
			return "Response{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", message='" + message + '\'' +
				'}';
		}
	}
}
