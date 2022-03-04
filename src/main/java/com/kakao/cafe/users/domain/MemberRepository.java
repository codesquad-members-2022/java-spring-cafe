package com.kakao.cafe.users.domain;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Long> save(Member user);

    Optional<Member> findById(Long id);

    Optional<Member> findByUserId(String userId);

    Optional<List<Member>> findAll();

    void deleteAll();
}
