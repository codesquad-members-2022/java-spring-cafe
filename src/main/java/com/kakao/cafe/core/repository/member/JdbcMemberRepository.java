package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import com.kakao.cafe.core.repository.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.kakao.cafe.utils.TimeUtils.dateTimeOf;
import static com.kakao.cafe.utils.TimeUtils.timestampOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private static final String ID = "id";

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member save(Member member) {
        if (!member.getId().equals(Integer.MAX_VALUE)) {
            update(member);
            return member;
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO members(id, email, password, nick_name, create_at) VALUES (null , ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{ID});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getNickName());
            ps.setTimestamp(4, timestampOf(member.getCreateAt()));
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        int id = key != null ? key.intValue() : -1;
        return new Member.Builder()
                .id(id)
                .nickName(member.getNickName())
                .build();
    }

    @Override
    public void update(Member t) {
        String query = "UPDATE members SET email=?, nick_name=? WHERE id=?";
        jdbcTemplate.update(query, t.getEmail(), t.getNickName(), t.getId());
    }

    @Override
    public Optional<Member> findById(Integer id) {
        String query = "SELECT * FROM members WHERE id=?";
        List<Member> results = jdbcTemplate.query(query, mapper, id);
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public List<Member> findAll() {
        String query = "SELECT * FROM members";
        List<Member> result = jdbcTemplate.query(query, mapper);
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        return result;
    }

    private static RowMapper<Member> mapper = (rs, rowNum) -> new Member.Builder()
            .id(rs.getInt("id"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .nickName(rs.getString("nick_name"))
            .createAt(dateTimeOf(rs.getTimestamp("create_at")))
            .build();
}
