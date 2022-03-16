package com.kakao.cafe.qna.infra;

import static com.kakao.cafe.common.utils.sql.SqlFormatter.*;
import static com.kakao.cafe.qna.infra.JdbcTemplateArticleRepository.ArticleColumns.*;
import static com.kakao.cafe.user.infra.MemoryUserRepository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.common.utils.sql.SqlColumns;
import com.kakao.cafe.qna.domain.Article;
import com.kakao.cafe.qna.domain.ArticleFactory;
import com.kakao.cafe.qna.domain.ArticleRepository;
import com.kakao.cafe.user.infra.JdbcTemplateUserRepository;

@Repository
@Primary
public class JdbcTemplateArticleRepository implements ArticleRepository {
	private static final String TABLE_NAME_OF_ARTICLE = "cafe_articles";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

	enum ArticleColumns implements SqlColumns {
		ALL("*", "", ""),
		ARTICLE_ID("article_id", ":article_id", ":articleId"),
		WRITER("writer", ":writer", ":writer"),
		TITLE("title", ":title", ":title"),
		CONTENT("content", ":content", ":content"),
		FK_CAFE_USER_ID("cafe_users_id", ":cafe_users_id", ":cafeUsersId"),
		WRITING_DATE("writing_date", ":writingDate", ":writingDate"),
		IS_DELETED("deleted", ":deleted", ":deleted"),
		COUNT_ALL("COUNT(*)", null, null),
		NONE(null, null, null);

		private final String columnName;
		private final String namedParameter;
		private final String updateParameter;

		ArticleColumns(String columnName, String namedParameter, String updateParameter) {
			this.columnName = columnName;
			this.namedParameter = namedParameter;
			this.updateParameter = updateParameter;
		}

		@Override
		public String getColumnName() {
			return columnName;
		}

		@Override
		public String getNamedParameter() {
			return namedParameter;
		}

		@Override
		public String getUpdateParameter() {
			return updateParameter;
		}
	}

	public JdbcTemplateArticleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
										JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Article save(Article entity) {
		if (!Objects.isNull(entity.getArticleId())) {
			updateArticle(entity);
			return entity;
		}
		insert(entity);
		return entity;
	}

	private void updateArticle(Article entity) {
		Optional<Article> article = findById(entity.getArticleId());
		if (article.isPresent()) {
			update(entity);
		}
	}

	private void update(Article entity) {
		String sql = getSqlOfUpdate(TABLE_NAME_OF_ARTICLE, List.of(WRITER, TITLE, CONTENT), ARTICLE_ID);
		SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
		int update = namedParameterJdbcTemplate.update(sql, params);
		logger.info("update user : {}", update);
	}

	private void insert(Article entity) {
		SimpleJdbcInsert simpleJdbcInsert =  new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName(TABLE_NAME_OF_ARTICLE).usingGeneratedKeyColumns(ARTICLE_ID.getColumnName());

		Map<String, Object> parameters = getArticleMap(entity);
		Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		entity.setArticleId(key.longValue());
	}

	private Map<String, Object> getArticleMap(Article entity) {
		Map<String, Object> parameters = new ConcurrentHashMap<>();
		parameters.put(WRITER.getColumnName(), entity.getWriter());
		parameters.put(TITLE.getColumnName(), entity.getTitle());
		parameters.put(CONTENT.getColumnName(), entity.getContent());
		parameters.put(FK_CAFE_USER_ID.getColumnName(), entity.getCafeUserId());
		parameters.put(WRITING_DATE.getColumnName(), entity.getWritingDate());
		parameters.put(IS_DELETED.getColumnName(), entity.isDeleted());
		return parameters;
	}

	@Override
	public Optional<Article> findById(Long id) {
		try {
			if (id < 1) {
				throw new IllegalArgumentException(ERROR_OF_USER_ID);
			}
		} catch (IllegalArgumentException exception) {
			logger.error("error of article db : {}", exception);
		}

		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ARTICLE_ID.getColumnName(), id);
		String sql = getSqlOfSelect(TABLE_NAME_OF_ARTICLE, List.of(ALL), ARTICLE_ID);
		List<Article> articles = namedParameterJdbcTemplate.query(sql, namedParameters, articleRowMapper());
		return articles.stream()
			.parallel()
			.findAny();
	}

	private RowMapper<Article> articleRowMapper() {
		return (rs, rowNum) -> {
			Article article = ArticleFactory.create(rs.getLong(ARTICLE_ID.getColumnName()),
				rs.getString(WRITER.getColumnName()),
				rs.getString(TITLE.getColumnName()),
				rs.getString(CONTENT.getColumnName()),
				rs.getLong(FK_CAFE_USER_ID.getColumnName()),
				rs.getObject(WRITING_DATE.getColumnName(), LocalDate.class),
				rs.getBoolean(IS_DELETED.getColumnName()));
			return article;
		};
	}

	@Override
	public void deleteAll() {
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("size", 1);
		this.namedParameterJdbcTemplate.update("DELETE FROM cafe_articles WHERE 1= :size", namedParameters);
	}

	@Override
	public List<Article> findAll() {
		return this.namedParameterJdbcTemplate.query(getSqlOfSelect(TABLE_NAME_OF_ARTICLE, List.of(ALL), NONE), articleRowMapper());
	}

	@Override
	public void delete(Long id) {
		String sql = String.format("DELETE FROM cafe_articles WHERE %s = %s", ARTICLE_ID.getColumnName(),
			ARTICLE_ID.getNamedParameter());
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(ARTICLE_ID.getColumnName(), id);
		this.namedParameterJdbcTemplate.update(sql, namedParameters);
	}
}
