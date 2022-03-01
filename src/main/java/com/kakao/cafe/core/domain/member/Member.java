package com.kakao.cafe.core.domain.member;


import java.time.LocalDateTime;

public class Member {

    private Long id;
    private String email;
    private String password;
    private String nickName;
    private LocalDateTime createAt;

    public Member (Long id){
        this.id = id;
    };

    public Member(Long id, String email, String password, String nickName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.createAt = getSignUpDate();
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

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public static class Builder {
        private Long id;
        private String email;
        private String password;
        private String nickName;
        private LocalDateTime createAt;

        public Builder(){};

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
}
