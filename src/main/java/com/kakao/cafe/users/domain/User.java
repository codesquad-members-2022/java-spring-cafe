package com.kakao.cafe.users.domain;

import com.kakao.cafe.exception.domain.InvalidFieldLengthException;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.controller.dto.UserJoinRequest;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private Long id;
    private final String userId;
    private final String passwd;
    private final String name;
    private final String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private User(Long id, String userId, String passwd, String name, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userId = userId;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
        this.createdDate = getOrDefault(createdDate, LocalDateTime.now());
        this.modifiedDate = getOrDefault(modifiedDate, LocalDateTime.now());

        validateRequiredField(this);
        validateFieldLength(this);
    }

    public static User createWithJoinRequest(UserJoinRequest joinRequest) {
        LocalDateTime now = LocalDateTime.now();

        return new User(
                null,
                joinRequest.getUserId(),
                joinRequest.getPasswd(),
                joinRequest.getName(),
                joinRequest.getEmail(),
                now,
                now);
    }

    // ---- public method ----
    public boolean equalsId(Long id) {
        return this.id.longValue() == id.longValue();
    }

    public boolean equalsUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean equalsPasswd(String passwd) {
        return this.passwd.equals(passwd);
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

    public boolean equalsEmail(String email) {
        return this.email.equals(email);
    }

    // ---- private method ----
    private LocalDateTime getOrDefault(LocalDateTime originValue, LocalDateTime defaultValue) {
        if (originValue == null) {
            return defaultValue;
        }
        return originValue;
    }

    private void validateRequiredField(User user) {
        if (user.getUserId() == null ||
                user.getPasswd() == null ||
                user.getName() == null ||
                user.getEmail() == null ) {
            throw new RequiredFieldNotFoundException();
        }
    }

    private void validateFieldLength(User user) {
        if (user.getUserId().length() > 20 ||
                user.getPasswd().length() > 41 ||
                user.getName().length() > 40 ||
                user.getEmail().length() > 320 ) {
            throw new InvalidFieldLengthException();
        }
    }

    // ---- getter setter ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getUserId().equals(user.getUserId()) &&
                getPasswd().equals(user.getPasswd()) &&
                getName().equals(user.getName()) &&
                getEmail().equals(user.getEmail()) &&
                Objects.equals(getCreatedDate(), user.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), user.getModifiedDate());
    }

    // builder
    public static class Builder {
        private Long id;
        private String userId;
        private String passwd;
        private String name;
        private String email;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setPasswd(String passwd) {
            this.passwd = passwd;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setModifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public User build() {
            return new User(id, userId, passwd, name, email, createdDate, modifiedDate);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
