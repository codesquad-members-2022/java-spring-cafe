package com.kakao.cafe.practice;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kakao.cafe.user.domain.User;

@SpringBootTest
public class DbText {
	Logger logger = LoggerFactory.getLogger(DbText.class);

	@Autowired
	DataSource dataSource;

	private Connection connection;  // ConnectionPoll 로부터 가져오기 때문에, @Autowired 사용 X

	@BeforeEach
	void setUp() throws SQLException {
		connection = dataSource.getConnection();
	}

	@Test
	void for_setting_db_data() throws SQLException {
		User user = new User("tester", "testName", "test@email.com", "1234asdf");
		String sql = "insert into cafe_users (user_id, name, email, password) values (?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getPassword());

		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			logger.info("result of save's id : {}", rs.getInt(1));
		}
	}

	@Test
	void driver_test() {
		assertThat(dataSource).isNotNull();
		assertThat(connection).isNotNull();
	}

	@Test
	void database_test() throws SQLException {
		Statement stmt = connection.createStatement();

		String sql = "select * from user";

		ResultSet rs = stmt.executeQuery(sql);

		SoftAssertions soft = new SoftAssertions();

		while (rs.next()) {
			long id = rs.getLong("id");
			String name = rs.getString("name");
			User user = new User(1L, "tester", "testName", "test@email.com", "1234asdf");

			soft.assertThat(user.getId()).isNotZero();
			soft.assertThat(user.getName().length()).isGreaterThan(2);

			logger.info("User : {}", user);
		}

		soft.assertAll();
	}

	@Test
	void pstmt() throws SQLException {
		final Long id = 1L;
		String sql = "select * from cafe_users where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setLong(1, id);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			User user = new User(rs.getLong("id"),
				rs.getString("userId"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getString("password"));

			assertThat(user.getId()).isEqualTo(id);
			logger.info("User : {}", user);
		}
	}
}
