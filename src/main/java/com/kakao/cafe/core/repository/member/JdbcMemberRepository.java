package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //jdbcTemplate.update(con -> {
            //PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users(seq,name,email,passwd,profile_image_url,login_count,last_login_at,create_at) VALUES (null,?,?,?,?,?,?,?)", new String[]{"seq"})));

        //});
        Number key = keyHolder.getKey();
        long sequence = key != null ? key.longValue() : -1;
        return null;
    }
}
