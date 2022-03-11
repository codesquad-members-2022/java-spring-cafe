package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private final List<Member> store = new ArrayList<>();
    private static final int CORRECT_INDEX = 1;

    @Override
    public Member save(Member member) {
        store.add(member);
        member.setUserIndex(store.indexOf(member) + CORRECT_INDEX);
        member.setJoinTime(LocalDateTime.now());
        return member;
    }

    @Override
    public Optional<Member> findByIndex(int userIndex) {
        return Optional.ofNullable(store.get(userIndex - CORRECT_INDEX));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return store;
    }

    public void clearStore() {
        store.clear();
    }
}
