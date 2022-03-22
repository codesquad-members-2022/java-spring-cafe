package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateQuestionRepository implements QuestionRepository{

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateQuestionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Question findById(Long id) {
        List<Question> questions = jdbcTemplate.query("select * from questionTbl where id = ?", questionRowMapper(), id);
        return questions.stream().findAny().orElse(null);
    }

    private RowMapper<Question> questionRowMapper() {
        return ((rs, rowNum) -> {
            Question question = new Question();
            question.setId(rs.getLong("id"));
            question.setWriter(rs.getString("writer"));
            question.setTitle(rs.getString("title"));
            question.setContents(rs.getString("contents"));
            return question;
        });
    }

    @Override
    public List<Question> findAll() {
        return jdbcTemplate.query("select * from questionTbl", questionRowMapper());
    }

    @Override
    public Question save(Question question) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("questionTbl").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", question.getWriter());
        parameters.put("title", question.getTitle());
        parameters.put("contents", question.getContents());

        Long key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)).longValue();
        log.info("key={}", key);
        question.setId(key);
        return question;
    }
}
