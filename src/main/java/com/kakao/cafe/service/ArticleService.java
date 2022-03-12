package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.dto.ArticleDetailDto;
import com.kakao.cafe.web.dto.ArticleListDto;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void register(ArticleRegisterFormDto articleRegisterFormDto) {
        articleRepository.save(articleRegisterFormDto.toEntity());
    }

    public List<ArticleListDto> showAll() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream()
            .map(article -> {
                int articleId = articleList.indexOf(article) + 1;
                return new ArticleListDto(article, articleId);
            })
            .collect(Collectors.toList());
    }

    public ArticleDetailDto showOne(String articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        return new ArticleDetailDto(validateExistenceArticleId(optionalArticle));
    }

    private Article validateExistenceArticleId(Optional<Article> optionalArticle) {
        return optionalArticle.orElseThrow(() -> {
            throw new IllegalStateException("해당 게시글이 존재하지 않습니다.");
        });
    }
}
