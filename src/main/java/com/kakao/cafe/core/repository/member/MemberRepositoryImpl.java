package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final List<Member> members = new ArrayList<>();

    @Override
    public Member insert(Member member) {
        members.add(member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    @Override
    public void update(Member member) {

    }
}
