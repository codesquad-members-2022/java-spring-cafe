package com.ttasjwi.cafe.domain.user;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private String userName; // 중복 허용 안 함
    private String userEmail; // 중복 허용 안 함
    private String password;
    private LocalDate regDate;

    public User(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.regDate = LocalDate.now();
    }

    private User(UserBuilder userBuilder) {
        this.userName = userBuilder.userName;
        this.userEmail = userBuilder.userEmail;
        this.password = userBuilder.password;
        this.regDate = userBuilder.regDate;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String userName;
        private String userEmail;
        private String password;
        private LocalDate regDate;

        private UserBuilder() {}

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder regDate(LocalDate regDate) {
            this.regDate = regDate;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

}
