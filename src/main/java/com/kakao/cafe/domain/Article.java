package com.kakao.cafe.domain;

import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Article {


    @Autowired
    private UserService userService;

    private static final Date date = new Date();
    private static int articleCount = 0;

    private int articleId;
    private String subject;
    private String content;
    private String uploadDate;
    private String writer;

    public int getArticleId() {
        return articleId;
    }


    @Autowired
    public Article(String userId, String subject, String content) {
        validUserId(userId);
        this.articleId = articleCount++;
        this.subject = subject;
        this.content = content;
        this.uploadDate = generateUploadDate();
        this.writer = userId;
    }

    private String generateUploadDate() {
        return date.toString();
    }

    private void validUserId(String userId) {
        if (userService.findOne(userId)==null){
            throw new RuntimeException();
        }
    }

}
