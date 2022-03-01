package com.kakao.cafe.domain.users;

import java.time.LocalDateTime;

public class User {

    private final Long id;
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
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
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
