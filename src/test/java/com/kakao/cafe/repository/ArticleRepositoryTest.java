package com.kakao.cafe.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class ArticleRepositoryTest {

    ArticleRepository articleRepository;

    @BeforeEach
    void init() {
        articleRepository = new ArticleRepository(new ArrayList<>());
    }


}