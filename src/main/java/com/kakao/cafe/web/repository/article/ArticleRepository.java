package com.kakao.cafe.web.repository.article;

import com.kakao.cafe.web.domain.article.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

	Article save(Article article);

	Optional<Article> findById(Long id);

	List<Article> findAll();

}
