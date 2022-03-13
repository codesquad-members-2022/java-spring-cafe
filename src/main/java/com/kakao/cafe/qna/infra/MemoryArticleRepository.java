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
		if (this.hasId(id)) {
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

		/*
			처음 도메인 객체 생성시 id 를 null 로 받아 생성되게 했습니다.
			id 가 null 일 경우, insert() 가 진행됩니다.
		 */
	private boolean hasId(Long id) {
		if (id == null) {
			return false;
		}
		Optional<Article> article = getArticleById(id);
		try {
			if (article.isEmpty()) {
				throw new DomainNotFoundException("없는 게시글 정보 요청입니다.");
			}
		} catch (DomainNotFoundException exception) {
			logger.error("no exist of article ; {}, {}", id, exception);
		}
		return article.get().isEquals(id);
	}

	@Override
	public Optional<Article> findById(Long id) {
		try {
			if (id < 1) {
				throw new IllegalArgumentException(ERROR_OF_ARTICLE_ID);
			}
			if (data.size() < id) {
				throw new DomainNotFoundException(ERROR_OF_ARTICLE_ID);
			}
		} catch (IllegalArgumentException | DomainNotFoundException exception) {
			logger.error("article db in findById : {}", exception);
		}

		return getArticleById(id);
	}

	private Optional<Article> getArticleById(Long id) {
		return data.stream()
			.parallel()
			.filter(it -> it.isEquals(id))
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
