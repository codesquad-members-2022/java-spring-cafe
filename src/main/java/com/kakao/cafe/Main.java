package com.kakao.cafe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.entity.ArticleEntity;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Article article = new Article(1, "writer", "title", "contents", LocalDate.now());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ArticleEntity map = objectMapper.convertValue(article, new TypeReference<>() {});
        System.out.println(map);

    }
}
