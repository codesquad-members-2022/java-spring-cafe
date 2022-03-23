package com.kakao.cafe.domain.article;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository {
    void save(Article article);

    List<Article> findAllDesc();

    Optional<Article> findById(Long id);

    void deleteById(Long id);
}
