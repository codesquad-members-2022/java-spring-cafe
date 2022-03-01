package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;

import java.util.List;

public interface MemberRepository {
    Member insert(Member member);
    List<Member> findAll();
    void update(Member member);
}
