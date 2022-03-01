package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final List<Member> members;

    MemberRepositoryImpl() {
        this.members = new ArrayList<>();
        members.add(new Member(1L, "jun@naver.com", "1234", "Jun"));
        members.add(new Member(2L, "minsong@naver.com", "1234", "MinJe"));
        members.add(new Member(3L, "pio@naver.com", "1234", "Pio"));
    }

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
    public int size() {
        return members.size();
    }

    @Override
    public void update(Member member) {

    }

    @Override
    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }
}
