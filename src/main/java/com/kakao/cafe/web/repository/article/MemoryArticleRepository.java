package com.kakao.cafe.web.repository.article;

import com.kakao.cafe.web.domain.article.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

	private static final Map<Long, Article> store = new ConcurrentHashMap<>();
	private static final AtomicLong sequence = new AtomicLong();

	@Override
	public Article save(Article article) {
		article.setId(sequence.incrementAndGet());
		store.put(article.getId(), article);
		return article;
	}

	@Override
	public Optional<Article> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<Article> findAll() {
		return new ArrayList<>(store.values());
	}

	public void clear() {
		store.clear();
	}
}
