package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private Integer id;
    private User user;
    private String title;
    private List<String> contents;
    private LocalDateTime createDate;

//    게시글을 등록할 때 사용하는 생성자
    public Article(User user, String title, List<String> contents) {
        this(null, user, title, contents, LocalDateTime.now());
    }

//    게시글을 데이터베이스에서 조회할 때 사용하는 생성자
    public Article(Integer id, User user, String title, List<String> contents, LocalDateTime createDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
    }

    public boolean isEqualsTitle(String title) {
        return this.title.equals(title);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
