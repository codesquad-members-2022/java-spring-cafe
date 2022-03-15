package com.kakao.cafe.web.repository.user;

import com.kakao.cafe.web.domain.user.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateUserRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public User save(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", user.getUserId());
		parameters.put("password", user.getPassword());
		parameters.put("name", user.getName());
		parameters.put("email", user.getEmail());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		user.setId(key.longValue());
		return user;
	}

	@Override
	public Optional<User> findById(Long id) {
		List<User> result = jdbcTemplate.query("SELECT * FROM USER WHERE id = ?", userRowMapper(), id);
		return result.stream().findAny();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		List<User> result = jdbcTemplate.query("SELECT * FROM USER WHERE email = ?", userRowMapper(), email);
		return result.stream().findAny();
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		List<User> result = jdbcTemplate.query("SELECT * FROM USER WHERE userId = ?", userRowMapper(), userId);
		return result.stream().findAny();
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM USER", userRowMapper());
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> {
			User user = new User(rs.getString("userId"), rs.getString("password"),
				rs.getString("name"), rs.getString("email"));
			user.setId(rs.getLong("id"));
			return user;
		};
	}

}
