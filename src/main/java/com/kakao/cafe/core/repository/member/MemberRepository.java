package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepository {

    private final List<Member> members;

    public MemberRepository() {
        this.members = new ArrayList<>();
    }

    public Member insert(Member member) {
        members.add(member);
        return member;
    }

    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    public Optional<Member> findById(int id) {
        return members.stream()
                .filter(user -> user.getId()==id)
                .findAny();
    }
}
