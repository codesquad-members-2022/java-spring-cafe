package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static com.kakao.cafe.utils.TimeUtils.timestampOf;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member persist(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO members(id, email, password, nick_name, create_at) VALUES (null , ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
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
                .email(member.getEmail())
                .createAt(member.getCreateAt())
                .build();
    }

    @Override
    public Optional<Member> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public void update(Member e) {

    }

    @Override
    public void delete(Member e) {

    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
