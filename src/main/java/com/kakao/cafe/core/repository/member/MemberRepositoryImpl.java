package com.kakao.cafe.core.repository.member;

import com.kakao.cafe.core.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final List<Member> members;

    public MemberRepositoryImpl() {
        this.members = new ArrayList<>();
        members.add(new Member(1L, "jun@naver.com", "1234", "Jun"));
        members.add(new Member(2L, "minsong@naver.com", "1234", "MinJe"));
        members.add(new Member(3L, "pio@naver.com", "1234", "Pio"));
        members.add(new Member(4L, "donggi@naver.com", "1234", "donggi"));
        members.add(new Member(5L, "Nohri@naver.com", "1234", "Nohri"));
        members.add(new Member(6L, "Vans@naver.com", "1234", "Vans"));
        members.add(new Member(7L, "Phill@naver.com", "1234", "Phill"));
        members.add(new Member(8L, "BC@naver.com", "1234", "BC"));
        members.add(new Member(9L, "Tany@naver.com", "1234", "Tany"));
        members.add(new Member(10L, "Hanse@naver.com", "1234", "Hanse"));
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
    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }
}
