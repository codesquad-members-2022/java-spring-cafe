package com.kakao.cafe.domain;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    private User(UserBuilder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.name = builder.name;
        this.email = builder.email;
    }

    public void update(User user) {
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
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

    // Builder pattern
    public static class UserBuilder {

        private String userId;
        private String password;
        private String name;
        private String email;

        public UserBuilder(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
