package com.kakao.cafe.core.domain.member;


import java.time.LocalDateTime;
import java.util.Objects;

public class Member {

    private Integer id;
    private String email;
    private String password;
    private String nickName;
    private LocalDateTime createAt;

    public Member(Integer id) {
        this.id = id;
    }

    public Member(Integer id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createAt = getSignUpDate();
    }

    public Member(Integer id, String email, String password, String nickName, LocalDateTime createAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createAt = createAt;
    }

    public Member(Integer id, String email, String nickName, LocalDateTime createAt) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.createAt = createAt;
    }

    private LocalDateTime getSignUpDate() {
        return LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public static class Builder {
        private Integer id;
        private String email;
        private String password;
        private String nickName;
        private LocalDateTime createAt;

        public Builder() {
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Member build() {
            return new Member(id, email, nickName, createAt);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
