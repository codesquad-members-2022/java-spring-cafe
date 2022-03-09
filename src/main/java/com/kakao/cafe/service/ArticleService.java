package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleForm;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.util.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Article write(ArticleForm articleForm) {
        // 회원가입하지 않은 유저 이름으로 글을 작성 시 예외 처리
        userRepository.findByUserId(articleForm.getWriter())
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return articleRepository.save(Mapper.map(articleForm, Article.class));
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findArticle(Integer articleId) {
        return articleRepository.findById(articleId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));
    }
}
