package com.kakao.cafe.domain;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String date;
    private String password;

    //TODO : User 안에서 matchesId() 같은걸 만들어서 직접 필드 값을 내보내지 않도록 하자
    // 그런데 이렇게 하면 검색할 값이 인자로 주어지는 경우에는 getter 없이 가능하지만, Service의
    // validateUniqueNickname()와 같이 인자 없이 user의 필드에 접근해야할 때는 getter를 쓰는 방법 외의 다른 방법을
    // 아직 찾지 못하였다. 일단 matches~()를 만들어 적용하긴 하였나, getter가 이미 있닌 상태에서 의미가 있는걸까?

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean matchesId(int id) {
        return this.id == id;
    }

    public boolean matchesNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean matchesEmail(String email) {
        return this.email.equals(email);
    }
}
