package com.kakao.cafe.user.infra;

import static com.kakao.cafe.user.infra.JdbcTemplateUserRepository.UserColumns.*;
import static com.kakao.cafe.user.infra.MemoryUserRepository.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

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

import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Repository
@Primary
public class JdbcTemplateUserRepository implements UserRepository {
	public static final String SQL_SUBSTITUTE = " = ";
	public static final String SQL_COMMA = ",";

	private static final String TABLE_NAME_OF_USER = "cafe_users";
	private static final String SQL_UPDATE_OF_USER = "update " + TABLE_NAME_OF_USER + " set ";
	private static final String SQL_SELECT_PREFIX_OF_USER = "select ";
	private static final String SQL_SELECT_POSTFIX_OF_USER = " from " + TABLE_NAME_OF_USER;
	private static final String SQL_CONDITIONAL = " where ";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

	enum UserColumns {
		ALL("*", "", ""),
		ID("id", ":id", ":id"),
		USER_ID("user_id", ":user_id", ":userId"),
		NAME("name", ":name", ":name"),
		EMAIL("email", ":email", ":email"),
		PASSWORD("password", ":password", "password"),
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

		public String getColumnName() {
			return columnName;
		}

		public String getNamedParameter() {
			return namedParameter;
		}

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
	public long save(User entity) {
		if (!Objects.isNull(entity.getId())) {
			updateUser(entity);
			return entity.getId();
		}
		insert(entity);
		return entity.getId();   // 객체 참조 활용 - but insert가 id 반환해서 받게, 명시적인게 더 좋을까?
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
		return parameters;
	}

	private void update(User entity) {
		String sql = getSqlOfUpdate(List.of(USER_ID, NAME, EMAIL), ID);
		SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
		int update = namedParameterJdbcTemplate.update(sql, params);
		logger.info("update user : {}", update);
	}

	public String getSqlOfUpdate(List<UserColumns> columns, UserColumns conditioner) {
		StringBuffer sb = new StringBuffer();
		sb.append(SQL_UPDATE_OF_USER);
		toMapper(columns, sb);
		sb.append(SQL_CONDITIONAL)
			.append(conditioner.getColumnName())
			.append(SQL_SUBSTITUTE)
			.append(conditioner.getNamedParameter());
		return sb.toString();
	}

	private void toMapper(List<UserColumns> columns, StringBuffer sb) {
		for (UserColumns column : columns) {
			sb.append(column.getColumnName())
				.append(SQL_SUBSTITUTE)
				.append(column.getUpdateParameter())
				.append(SQL_COMMA);
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	@Override
	public Optional<User> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_USER_ID);
		}
		final SqlParameterSource namedParameters = new MapSqlParameterSource()
														.addValue(ID.getColumnName(), id);
		String sql = getSqlOfSelect(List.of(ALL), ID);
		List<User> users = namedParameterJdbcTemplate.query(sql, namedParameters, userRowMapper());
		// List<User> users = jdbcTemplate.query("select * from cafe_users where id = ?", userRowMapper(), id);
		return users.stream()
			.parallel()
			.findAny();
	}

	private void addColumnNames(List<UserColumns> columns, StringBuffer sb) {
		for (UserColumns column : columns) {
			sb.append(column.columnName)
				.append(SQL_COMMA);
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> {
			User user = new User(rs.getLong(ID.getColumnName()),
				rs.getString(USER_ID.getColumnName()),
				rs.getString(NAME.getColumnName()),
				rs.getString(EMAIL.getColumnName()),
				rs.getString(PASSWORD.getColumnName()));
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
		return this.namedParameterJdbcTemplate.query(getSqlOfSelect(List.of(ALL), NONE), userRowMapper());
	}

	@Override
	public boolean existByUserId(String userId) {
		// String sql = "SELECT COUNT(*) FROM cafe_users WHERE user_id = :user_id";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(USER_ID.getColumnName(), userId);
		String sql = getSqlOfSelect(List.of(COUNT_ALL), USER_ID);
		return getNumberOfExistUserId(sql, namedParameters) != 0;
	}

	@Override
	public boolean existByName(String name) {
		// String sql = "SELECT COUNT(*) FROM cafe_users WHERE name = :name";
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(NAME.getColumnName(), name);
		String sql = getSqlOfSelect(List.of(COUNT_ALL), NAME);
		return getNumberOfExistUserId(sql, namedParameters) != 0;
	}

	private Integer getNumberOfExistUserId(String sql, SqlParameterSource namedParameters) {
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
	}

	private String getSqlOfSelect(List<UserColumns> columns, UserColumns conditioner) {
		StringBuffer sb = new StringBuffer();
		sb.append(SQL_SELECT_PREFIX_OF_USER);
		addColumnNames(columns, sb);
		sb.append(SQL_SELECT_POSTFIX_OF_USER);

		if (!Objects.isNull(conditioner.columnName)) {
			sb.append(SQL_CONDITIONAL)
				.append(conditioner.getColumnName())
				.append(SQL_SUBSTITUTE)
				.append(conditioner.getNamedParameter());
		}
		return sb.toString();
	}
}
