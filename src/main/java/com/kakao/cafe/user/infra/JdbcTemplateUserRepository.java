package com.kakao.cafe.user.infra;

import static com.kakao.cafe.common.utils.sql.SqlFormatter.*;
import static com.kakao.cafe.user.infra.JdbcTemplateUserRepository.UserColumns.*;
import static com.kakao.cafe.user.infra.MemoryUserRepository.*;

import java.time.LocalDateTime;
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
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Repository
@Primary
public class JdbcTemplateUserRepository implements UserRepository {
	private static final String TABLE_NAME_OF_USER = "cafe_users";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

	enum UserColumns implements SqlColumns {
		ALL("*", "", ""),
		ID("id", ":id", ":id"),
		USER_ID("user_id", ":user_id", ":userId"),
		NAME("name", ":name", ":name"),
		EMAIL("email", ":email", ":email"),
		PASSWORD("password", ":password", ":password"),
		CREATED_DATE("created_date", ":created_date", ":createdDate"),
		LAST_UPDATED_DATE("last_updated_date", ":last_updated_date", ":lastUpdatedDate"),
		RESTRICTED_ENTER_PASSWORD("restricted_enter_password", ":restricted_enter_password", ":restrictedEnterPassword"),
		COUNT_ALL("COUNT(*)", null, null),
		NONE(null, null, null);

		private final String columnName;
		private final String namedParameter;
		private final String updateParameter;

		UserColumns(String columnName, String namedParameter, String updateParameter) {
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

	public JdbcTemplateUserRepository(
		NamedParameterJdbcTemplate namedParameterJdbcTemplate,
		JdbcTemplate jdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User save(User entity) {
		if (!Objects.isNull(entity.getId())) {
			updateUser(entity);
			return entity;
		}
		insert(entity);
		return entity;
	}

	private void updateUser(User entity) {
		Optional<User> user = findById(entity.getId());
		if (user.isPresent()) {
			update(entity);
		}
	}

	private void insert(User entity) {
		SimpleJdbcInsert simpleJdbcInsert =  new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName(TABLE_NAME_OF_USER).usingGeneratedKeyColumns(ID.getColumnName());

		Map<String, Object> parameters = getUserMap(entity);
		Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		entity.setId(key.longValue());
	}

	private Map<String, Object> getUserMap(User entity) {
		Map<String, Object> parameters = new ConcurrentHashMap<>();
		parameters.put(USER_ID.getColumnName(), entity.getUserId());
		parameters.put(NAME.getColumnName(), entity.getName());
		parameters.put(EMAIL.getColumnName(), entity.getEmail());
		parameters.put(PASSWORD.getColumnName(), entity.getPassword());
		parameters.put(RESTRICTED_ENTER_PASSWORD.getColumnName(), entity.isRestrictedEnterPassword());
		parameters.put(CREATED_DATE.getColumnName(), entity.getCreatedDate());
		parameters.put(LAST_UPDATED_DATE.getColumnName(), entity.getLastUpdatedDate());
		return parameters;
	}

	private void update(User entity) {
		String sql = getSqlOfUpdate(TABLE_NAME_OF_USER, List.of(NAME, EMAIL, RESTRICTED_ENTER_PASSWORD, LAST_UPDATED_DATE), ID);
		SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
		int update = namedParameterJdbcTemplate.update(sql, params);
		logger.info("update user (1:ok): {}", update);
	}

	@Override
	public Optional<User> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_USER_ID);
		}
		final SqlParameterSource namedParameters = new MapSqlParameterSource()
														.addValue(ID.getColumnName(), id);
		String sql = getSqlOfSelect(TABLE_NAME_OF_USER, List.of(ALL), ID);
		List<User> users = namedParameterJdbcTemplate.query(sql, namedParameters, userRowMapper());
		// List<User> users = jdbcTemplate.query("select * from cafe_users where id = ?", userRowMapper(), id);
		return users.stream()
			.parallel()
			.findAny();
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> {
			User user = new User(rs.getLong(ID.getColumnName()),
				rs.getString(USER_ID.getColumnName()),
				rs.getString(PASSWORD.getColumnName()),
				rs.getString(NAME.getColumnName()),
				rs.getString(EMAIL.getColumnName()),
				rs.getObject(CREATED_DATE.getColumnName(), LocalDateTime.class),
				rs.getObject(LAST_UPDATED_DATE.getColumnName(), LocalDateTime.class),
				rs.getBoolean(RESTRICTED_ENTER_PASSWORD.getColumnName()));
			return user;
		};
	}

	@Override
	public void deleteAll() {
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("size", 1);
		this.namedParameterJdbcTemplate.update("DELETE FROM cafe_users WHERE 1= :size", namedParameters);
	}

	@Override
	public List<User> findAll() {
		// return this.namedParameterJdbcTemplate.query("select * from cafe_users", userRowMapper());
		return this.namedParameterJdbcTemplate.query(getSqlOfSelect(TABLE_NAME_OF_USER, List.of(ALL), NONE), userRowMapper());
	}

	@Override
	public boolean existByUserId(String userId) {
		// String sql = "SELECT COUNT(*) FROM cafe_users WHERE user_id = :user_id";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(USER_ID.getColumnName(), userId);
		String sql = getSqlOfSelect(TABLE_NAME_OF_USER, List.of(COUNT_ALL), USER_ID);
		return getNumberOfExistUserId(sql, namedParameters) != 0;
	}

	@Override
	public boolean existByName(String name) {
		// String sql = "SELECT COUNT(*) FROM cafe_users WHERE name = :name";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(NAME.getColumnName(), name);
		String sql = getSqlOfSelect(TABLE_NAME_OF_USER, List.of(COUNT_ALL), NAME);
		return getNumberOfExistUserId(sql, namedParameters) != 0;
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		String sql = "SELECT * FROM cafe_users WHERE user_id = :user_id";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(USER_ID.getColumnName(), userId);
		User user = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, userRowMapper());
		return Optional.of(user);
	}

	private Integer getNumberOfExistUserId(String sql, SqlParameterSource namedParameters) {
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
	}
}
