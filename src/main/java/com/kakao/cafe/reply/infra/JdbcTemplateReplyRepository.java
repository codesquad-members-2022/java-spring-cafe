package com.kakao.cafe.reply.infra;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.reply.domain.Reply;
import com.kakao.cafe.reply.domain.ReplyFactory;
import com.kakao.cafe.reply.domain.ReplyRepository;

@Repository
public class JdbcTemplateReplyRepository implements ReplyRepository {
	private static final String TABLE_NAME_OF_REPLIES = "cafe_replies";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(JdbcTemplateReplyRepository.class);

	public JdbcTemplateReplyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
										JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Reply save(Reply entity) {
		if (!Objects.isNull(entity.getReplyId())) {
			updateReply(entity);
			return entity;
		}
		insert(entity);
		return entity;
	}

	private void updateReply(Reply entity) {
		Optional<Reply> reply = findById(entity.getReplyId());
		if (reply.isPresent()) {
			update(entity);
		}
	}

	private void update(Reply entity) {
		String sql = "update cafe_replies set contet = :content where reply_id = :replyId";
		SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
		int update = namedParameterJdbcTemplate.update(sql, params);
		logger.info("update article : {}", update);
	}

	private void insert(Reply entity) {
		SimpleJdbcInsert simpleJdbcInsert =  new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName(TABLE_NAME_OF_REPLIES).usingGeneratedKeyColumns("reply_id");

		Map<String, Object> parameters = getReplyMap(entity);
		Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		entity.setReplyId(key.longValue());
	}

	private Map<String, Object> getReplyMap(Reply entity) {
		Map<String, Object> parameters = new ConcurrentHashMap<>();
		parameters.put("replier", entity.getReplier());
		parameters.put("content", entity.getContent());
		parameters.put("writing_date", entity.getWritingDate());
		parameters.put("cafe_users_user_id", entity.getCafeUsersId());
		parameters.put("cafe_users_id", entity.getCafeId());
		parameters.put("cafe_article_id", entity.getArticleId());
		parameters.put("deleted", entity.isDeleted());
		return parameters;
	}

	@Override
	public Optional<Reply> findById(Long aLong) {
		return Optional.empty();
	}

	@Override
	public void deleteAll() {

	}

	@Override
	public List<Reply> findByArticleId(long articleId) {
		String sql = "SELECT * FROM CAFE_REPLIES where cafe_article_id = :cafe_article_id";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cafe_article_id", articleId);
		return this.namedParameterJdbcTemplate.query(sql, namedParameters, articleRowMapper());
	}

	private RowMapper<Reply> articleRowMapper() {
		return (rs, rowNum) -> {
			Reply article = ReplyFactory.loadOf(rs.getLong("reply_id"),
				rs.getString("replier"),
				rs.getString("content"),
				rs.getObject("writing_date", LocalDate.class),
				rs.getLong("cafe_article_id"),
				rs.getString("cafe_users_user_id"),
				rs.getLong("cafe_users_id"),
				rs.getBoolean("deleted"));
			return article;
		};
	}
}
