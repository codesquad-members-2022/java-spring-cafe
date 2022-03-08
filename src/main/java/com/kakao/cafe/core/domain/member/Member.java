package com.kakao.cafe.core.domain.member;


import java.time.LocalDateTime;
import java.util.Objects;

public class Member {

    private Long id;
    private String email;
    private String password;
    private String nickName;
    private LocalDateTime createAt;

    public Member (Long id){
        this.id = id;
    }

    public Member(Long id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createAt = getSignUpDate();
    }

    public Member (String email, String nickName){
        this.email = email;
        this.nickName = nickName;
    }

    private LocalDateTime getSignUpDate() {
        return LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
        private Long id;
        private String email;
        private String password;
        private String nickName;
        private LocalDateTime createAt;

        public Builder(Member member) {
            this.id = member.id;
            this.email = member.email;
            this.password = member.password;
            this.nickName = member.nickName;
            this.createAt = member.createAt;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder nickName(String nickName){
            this.nickName = nickName;
            return this;
        }

        public Member build() {
            return new Member(id);
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
        return 3;
    }
}
