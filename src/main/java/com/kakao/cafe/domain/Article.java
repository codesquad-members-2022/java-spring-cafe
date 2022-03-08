package com.kakao.cafe.domain;

import com.kakao.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Article {

    private final UserService userService;
    private static final Date date = new Date();

    private String subject;
    private String content;
    private String uploadDate;
    private String writer;

    @Autowired
    public Article(UserService userService, String userId, String subject, String content) {
        this.userService = userService;
        validUserId(userId);
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
