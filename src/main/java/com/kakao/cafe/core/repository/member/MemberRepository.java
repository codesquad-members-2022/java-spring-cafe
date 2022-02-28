package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;

public interface MemberRepository {
    Member insert(Member member);
}
