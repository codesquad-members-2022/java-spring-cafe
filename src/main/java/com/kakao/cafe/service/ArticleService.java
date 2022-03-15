package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleResponse;
import com.kakao.cafe.dto.ArticleSaveRequest;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.session.SessionUser;
import com.kakao.cafe.util.Mapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleResponse write(SessionUser user, ArticleSaveRequest request) {
        // request 객체에 writer 설정
        request.setWriter(user.getUserId());

        // ArticleSaveRequest DTO 객체를 Article 도메인 객체로 변환
        Article article = Mapper.map(request, Article.class);

        // Article 도메인 객체를 저장소에 저장
        Article savedArticle = articleRepository.save(article);

        // Article 도메인 객체로부터 ArticleResponse DTO 객체로 변환
        return Mapper.map(savedArticle, ArticleResponse.class);
    }

    public List<ArticleResponse> findArticles() {
        // List<Article> 도메인 객체를 저장소로부터 반환
        List<Article> articles = articleRepository.findAll();

        // List<Article> 도메인 객체를 List<ArticleResponse> DTO 객체로 변환
        return articles.stream()
            .map(article -> Mapper.map(article, ArticleResponse.class))
            .collect(Collectors.toList());
    }

    public ArticleResponse findArticle(Integer articleId) {
        // Article 도메인 객체를 저장소로부터 반환
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // Article 도메인 객체를 ArticleResponse 도메인 객체로 변환
        return Mapper.map(article, ArticleResponse.class);
    }

    public ArticleResponse mapUserArticle(SessionUser user, Integer articleId) {
        Article article = findUserArticle(user, articleId);

        // Article 도메인 객체를 ArticleResponse DTO 로 변환
        return Mapper.map(article, ArticleResponse.class);
    }

    public ArticleResponse updateArticle(SessionUser user, ArticleSaveRequest request,
        Integer articleId) {
        Article article = findUserArticle(user, articleId);

        // Article 도메인 객체에 대해 업데이트 요청사항을 변경
        Article updatedArticle = article.update(request.getTitle(), request.getContents());

        // 저장소에 업데이트된 Article 객체를 저장
        Article savedArticle = articleRepository.save(updatedArticle);

        // Article 도메인 객체를 ArticleResponse 객체로 변환
        return Mapper.map(savedArticle, ArticleResponse.class);
    }


    public void deleteArticle(SessionUser user, Integer articleId) {
        findUserArticle(user, articleId);

        articleRepository.deleteById(articleId);
    }

    private Article findUserArticle(SessionUser user, Integer articleId) {
        // Article 도메인 객체를 저장소로부터 반환
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND));

        // 요청한 유저가 작성한 Article 객체인지 검증
        validateUser(user, article);
        return article;
    }

    private void validateUser(SessionUser user, Article article) {
        if (!article.checkWriter(user.getUserId())) {
            throw new InvalidRequestException(ErrorCode.INVALID_ARTICLE_WRITER);
        }
    }

}
