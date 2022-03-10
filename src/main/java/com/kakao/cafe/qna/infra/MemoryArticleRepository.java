package com.kakao.cafe.qna.infra;

import static java.util.stream.Collectors.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.qna.domain.Article;
import com.kakao.cafe.qna.domain.ArticleRepository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
	private static final String ERROR_OF_ARTICLE_ID = "article id";

	private final Map<Long, Article> data = new LinkedHashMap<>();
	private Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

	@Override
	public Article save(Article entity) {
		Long id = entity.getId();
		if (id != null && data.containsKey(id)) {
			data.replace(id,
				new Article(id,
					entity.getWriter(),
					entity.getTitle(),
					entity.getContent(),
					entity.getWritingDate()));
			return entity;
		}
		id = getNextId();
		data.put(id, entity);
		entity.setId(id);
		logger.info("question db save : {}",entity);
		return entity;
	}

	private Long getNextId() {
		return data.size()+1L;
	}

	@Override
	public Optional<Article> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_ARTICLE_ID);
		}
		if (!data.containsKey(id)) {
			throw new DomainNotFoundException(ERROR_OF_ARTICLE_ID);
		}
		Article article = data.get(id);
		return Optional.of(article);
	}

	@Override
	public List<Article> findAll() {
		return this.data.keySet().stream()
			.map(id -> this.data.get(id))
			.collect(toUnmodifiableList());
	}

	@Override
	public void deleteAll() {
		this.data.clear();
	}
}
