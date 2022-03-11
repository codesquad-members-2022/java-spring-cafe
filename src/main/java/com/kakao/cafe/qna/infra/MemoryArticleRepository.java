package com.kakao.cafe.qna.infra;

import static com.kakao.cafe.common.utils.sql.JdbcTypeConvertor.*;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.qna.domain.Article;
import com.kakao.cafe.qna.domain.ArticleRepository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
	private static final String ERROR_OF_ARTICLE_ID = "article id";

	private final List<Article> data = new CopyOnWriteArrayList<>();
	private Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

	@Override
	public Article save(Article entity) {
		Long id = entity.getId();
		if (id != null && this.hasId(id)) {
			data.set(getDataIdx(entity.getId()), entity);
			logger.info("question db update : {}", entity.getId());
			return entity;
		}
		id = getNextId(data.size());  // JdbcTypeConvertor 로 메서드를 분리하고 보니, 해당 데이터를 꺼내서 보내주는게 가독성은 안 좋아 보입니다.
		entity.setId(id);
		data.add(entity);
		logger.info("question db insert : {}", entity.getId());
		return entity;
		}

	private boolean hasId(Long id) {
		Optional<Article> article = getArticleId(id);
		if (article.isEmpty()) {
			logger.error("not exist of article id : {}", id);
			throw new DomainNotFoundException("없는 게시글 정보 요청입니다.");
		}
		return article.get().hasId(id);
	}

	@Override
	public Optional<Article> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_ARTICLE_ID);
		}
		if (data.size() < id) {
			throw new DomainNotFoundException(ERROR_OF_ARTICLE_ID);
		}
		return getArticleId(id);
	}

	private Optional<Article> getArticleId(Long id) {
		return data.stream()
			.parallel()
			.filter(it -> it.hasId(id))
			.findAny();
	}

	@Override
	public List<Article> findAll() {
		return this.data.stream()
			.collect(toUnmodifiableList());
	}

	@Override
	public void deleteAll() {
		this.data.clear();
	}
}
