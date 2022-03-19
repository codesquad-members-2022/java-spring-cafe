package com.kakao.cafe.core.repository;

import com.kakao.cafe.core.domain.member.Member;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Integer> {
    List<Member> findAll();
}
