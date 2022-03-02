package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member insert(Member member);
    List<Member> findAll();
    Optional<Member> findById(Long userId);
}
